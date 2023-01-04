package com.andersen.corgisteam.corgiroulette.service.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }
}
