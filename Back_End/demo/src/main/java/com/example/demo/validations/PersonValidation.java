package com.example.demo.validations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.errs.TypeError;
import com.example.demo.model.Appointment;
import com.example.demo.model.Person;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PersonRepository;

import jakarta.validation.ValidationException;

@Component
public class PersonValidation extends Validation {

    private PersonRepository personRepository;
    private AppointmentRepository appointmentRepository;

    public PersonValidation(PersonRepository personRepository, AppointmentRepository appointmentRepository) {
        this.personRepository = personRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }

        try {
            UUID.fromString(id); // sera que id foi
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID deve ser no formato UUID requerido pelo sistema", e);
        }
    }

    public void create(Person person) {
        isNullOrEmpty(
                new TypeError("Informe o e-mail", person.getEmail()),
                new TypeError("Informe o nome", person.getName()),
                new TypeError("Informe a Senha", person.getPassword()),
                new TypeError("Informe o telefone", person.getTelephone()));

        if (personRepository.existsByEmail(person.getEmail()))
            throw new IllegalArgumentException("E-mail já dastrado.");

        validateEmailFormat(person.getEmail());
    }

    public Optional<Person> login(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder) {

        isNullOrEmpty(
                new TypeError("Informe o E-mail.", loginRequestDto.email()),
                new TypeError("Informe a senha", loginRequestDto.password()));

        // busca email
        Optional<Person> personEmail = personRepository.findByEmail(loginRequestDto.email());

        if (personEmail.isEmpty())
            return Optional.empty();

        // senha digitada = hash armazenado
        Person person = personEmail.get();

        if (!passwordEncoder.matches(loginRequestDto.password(),
                person.getPassword()))
            return Optional.empty(); // senha errada

        return personEmail;
    }

    public void validatePasswordStrength(String password) {
        if (password == null || password.isEmpty())
            throw new ValidationException("A senha não pode ser nada");

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

    public void validateEmailFormat(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email não pode ser vazio");
        }

        boolean hasSpecialChars = email.split("@")[0].matches(".*[@#$%^&+=!].*");
        boolean startsOrEndsWithDot = email.startsWith(".") || email.startsWith("-") ||
                email.endsWith(".") || email.endsWith("-");
        boolean hasSpaces = email.contains(" ");
        boolean hasAt = email.contains("@");
        boolean hasValidDomain = email.matches(".*@.+\\..+");

        StringBuilder errorMessage = new StringBuilder();

        if (hasSpecialChars) {
            errorMessage.append("Email não deve conter caracteres especiais na parte local\n");
        }
        if (startsOrEndsWithDot) {
            errorMessage.append("Email não pode começar ou terminar com . ou -\n");
        }
        if (hasSpaces) {
            errorMessage.append("Email não pode conter espaços\n");
        }
        if (!hasAt) {
            errorMessage.append("Email deve conter @\n");
        }
        if (!hasValidDomain) {
            errorMessage.append("Domínio do email inválido\n");
        }

        if (errorMessage.length() > 0) {
            throw new ValidationException(errorMessage.toString() +
                    "Exemplo válido: usuario@dominio.com");
        }
    }

    public void validateTelephone(String telephone) {
        String regex = "^\\+?[1-9]\\d{1,14}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(telephone.replaceAll("\\D", ""));

        if (matcher.matches()) {
            System.err.println("ok telefone ^^");
        } else {
            throw new ValidationException("Telefone não valido");
        }
    }

    public boolean appointmentNotFinished(Person person) {
        System.out.println("certo");

        List<Appointment> appointments = appointmentRepository.findByFkPatient(person);

        // retorna false, pois não possui consultas
        if (appointments.isEmpty())
            return false;

        // Retorna true caso exista alguma consulta depois da data ou hora atuais
        for (Appointment appointment : appointments) {
            if (appointment.getFkAgenda().getLocalDate().isAfter(LocalDate.now())
                    || (appointment.getFkAgenda().getLocalDate().isEqual(LocalDate.now())
                            && appointment.getFkAgenda().getLocalTime().isAfter(LocalTime.now())))
                return true;
        }

        // Ok, não possui consultas pendentes;
        return true;
    }

}
