package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Person;

@Component
public class Validation {
    List<String> invalidFiels = new ArrayList<>();

    public boolean isNullOrEmpty(String data) {
        return data.isEmpty() || data == null;
    }

    public void clearInvalidFields() {
        if (!invalidFiels.isEmpty())
            invalidFiels = new ArrayList<>();
    }

    public void validatePersonUpdate(Person person){
        if(person.getEmail() != null){
            throw new IllegalStateException("Email já está em uso por alguem!");
        }
    }
}
