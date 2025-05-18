package com.example.demo.errs;

public record TypeError(String name, String value) {
    public static TypeError fromTypeError(String name, String value) {
        return new TypeError(name, value);
    }

    @Override
    public final String toString() {
        return String.format("%s", name);
    }
}
