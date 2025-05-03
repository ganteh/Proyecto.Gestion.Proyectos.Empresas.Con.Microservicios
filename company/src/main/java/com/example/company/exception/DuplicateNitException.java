package com.example.company.exception;

public class DuplicateNitException extends RuntimeException {
    public DuplicateNitException(String message) {
        super(message);
    }
}