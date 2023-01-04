package com.andersen.corgisteam.corgiroulette.service.exception;

public class RequiredFieldIsEmptyException extends ValidationException {

    public RequiredFieldIsEmptyException(String message) {
        super(message);
    }
}
