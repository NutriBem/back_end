package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

import jakarta.validation.ValidationException;

@Component
public class PersonValidation extends Validation {

    private PersonRepository personRepository;

    public PersonValidation(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void create(Person person) {
        clearInvalidFields();

        if (isNullOrEmpty(person.getEmail())) invalidFiels.add("E-mail");
        if (isNullOrEmpty(person.getName())) invalidFiels.add("Nome");
        if (isNullOrEmpty(person.getPassword())) invalidFiels.add("Senha");
        if (isNullOrEmpty(person.getTelephone())) invalidFiels.add("Telefone");

        // inserir as validações de senha

        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos inválidos: " + invalidFiels);

        if (personRepository.existsByEmail(person.getEmail()))
            throw new IllegalArgumentException("E-mail já dastrado.");
    }

    public Optional<Person> login(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder) {
        clearInvalidFields();

        // validação obrigatoria]
        if (isNullOrEmpty(loginRequestDto.email()))
            invalidFiels.add("E-mail invalido");
        if (isNullOrEmpty(loginRequestDto.password()))
            invalidFiels.add("Senha invalida");

        if (!invalidFiels.isEmpty())
            throw new IllegalArgumentException("Campos invalidos! " + invalidFiels);

        // busca email
        Optional<Person> personEmail = personRepository.findByEmail(loginRequestDto.email());
        if (personEmail.isEmpty())
            return Optional.empty();

        // senha digitada = hash armazenado
        Person person = personEmail.get();
        if (!passwordEncoder.matches(loginRequestDto.password(), person.getPassword()))
            return Optional.empty(); // senha errada

        return personEmail;
    }

    public void validatePasswordStrength(String password) {
        if (password == null || password.isEmpty())
            throw new ValidationException("A senha não pode ser nada");

        System.out.println("Validando senha: " + password); // DEBUG

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[@#$%^&+=!].*");
        boolean hasLength = password.length() >= 8;

        System.out.printf("Resultados: Maiúscula=%b, Número=%b, Especial=%b, Tamanho=%b%n",
                hasUpper, hasDigit, hasSpecial, hasLength); // DEBUG

        if (!(hasUpper && hasDigit && hasSpecial && hasLength)) {
            throw new ValidationException(
                    "Senha deve conter:\n" +
                            (hasUpper ? "" : "- Pelo menos 1 letra maiúscula\n") +
                            (hasDigit ? "" : "- Pelo menos 1 número\n") +
                            (hasSpecial ? "" : "- Pelo menos 1 caractere especial (@#$%^&+=!)\n") +
                            (hasLength ? "" : "- Mínimo de 8 caracteres\n") +
                            "Exemplo válido: Senha@1234");
        }
    }

}
