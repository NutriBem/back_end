package com.example.demo.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

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
}
