package com.example;

public class ExceptionThrower {
    public void throwException(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        // Other logic could go here
        System.out.println("Input is valid: " + input);
    }
}
