package com.example.demo.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.PersonResponseDto;
import com.example.demo.errs.TypeError;
import com.example.demo.model.ImageData;
import com.example.demo.model.Person;
import com.example.demo.repository.ImageDataRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.validations.PersonValidation;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private PersonValidation personValidation;
    private PasswordEncoder passwordEncoder;
    private ImageDataRepository imageDataRepository;

    public PersonService(
            PersonRepository personRepository,
            PersonValidation personValidation,
            PasswordEncoder passwordEncoder,
            ImageDataRepository imageDataRepository) {
        this.personRepository = personRepository;
        this.personValidation = personValidation;
        this.passwordEncoder = passwordEncoder;
        this.imageDataRepository = imageDataRepository;
    }

    public Optional<PersonResponseDto> getById(String id) {
        try {
            personValidation.validateId(id);
            UUID idParseString = UUID.fromString(id);
            return personRepository.findById(idParseString).map(PersonResponseDto::fromEntity);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e);
        }
    }

    public void deleteById(UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isEmpty())
            throw new IllegalArgumentException("Usuário não encontrado");

        Person person = personOptional.get();

        // verificar se o usuário possui consultas não finalizadas;
        boolean result = personValidation.appointmentNotFinished(person);

        if (result)
            throw new IllegalArgumentException("Não é possível excluir a sua conta, pois há consultas não concluídas.");

        personRepository.delete(personOptional.get());
    }

    public Optional<Person> login(LoginRequestDto loginRequest) {
        return personValidation.login(loginRequest, passwordEncoder);
    }

    protected <T extends Person> T createPerson(T person) {
        personValidation.validatePasswordStrength(person.getPassword());

        person.setPassword(passwordEncoder.encode(person.getPassword()));

        return personRepository.save(person);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    public <T extends Person> T updatePerson(UUID id, T updatePerson) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        System.out.println(existingPerson.getName()); // Correto!

        // if (existingPerson.getClass().getName().equals(Recepcionist.class.getName()))
        // {
        // throw new IllegalArgumentException("Tipo de pessoa incompatível para
        // atualização");
        // }

        if (!existingPerson.getEmail().equals(updatePerson.getEmail())) {
            if (personRepository.existsByEmail(updatePerson.getEmail())) {
                throw new IllegalStateException("Este email já está em uso por outro usuário");
            }
        }

        // {
        // "name": "tesste",
        // "email": "testedsads6",
        // "telephone": "123457894"
        // }

        personValidation.validatePersonUpdate(id, updatePerson.getEmail());

        Person newPerson = updateFields(existingPerson, updatePerson);
        // if (!existingPerson.getEmail().equals(updatedPerson.getEmail())) {
        // validateEmail(updatedPerson.getEmail());
        // }

        System.out.println("NEW PERSON:" + newPerson);

        @SuppressWarnings("unchecked")
        T savedPerson = (T) personRepository.save(newPerson);

        return savedPerson;
    }

    private <T extends Person> T updateFields(T existP, T updateP) {
        existP.setName(updateP.getName());
        existP.setEmail(updateP.getEmail());
        existP.setTelephone(updateP.getTelephone());

        return existP;
    }

    public void updatePassword(UUID id, String newPassword) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        person.setPassword(passwordEncoder.encode(newPassword));
        personRepository.save(person);
    }

    public Long saveImage(MultipartFile file, String personId) throws IOException {
        personValidation.isNullOrEmpty(new TypeError("Informe o id do usuário", personId));

        Optional<Person> person = personRepository.findById(UUID.fromString(personId));

        if (person.isEmpty())
            throw new IllegalArgumentException("Usuário não encontrado.");

        ImageData imageData = new ImageData();
        imageData.setFileName(file.getOriginalFilename());
        imageData.setContentType(file.getContentType());
        imageData.setData(file.getBytes());

        ImageData newImageData = imageDataRepository.save(imageData);

        person.get().setImageData(newImageData); // salva a imagem no atributo 'imageData' do Person
        personRepository.save(person.get()); // altera o usuário
        return newImageData.getId();
    }

    public byte[] getImage(String id) {
        personValidation.isNullOrEmpty(new TypeError("Informe o id do usuário", id));
        Optional<Person> person = personRepository.findById(UUID.fromString(id));

        if (person.isEmpty())
            throw new IllegalArgumentException("Usuário não encontrado.");

        return person.get().getImageData().getData();
    }
}
