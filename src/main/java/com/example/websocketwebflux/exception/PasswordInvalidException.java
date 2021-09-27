package com.example.websocketwebflux.exception;

public class PasswordInvalidException extends ErrorModel {

    public PasswordInvalidException() {
        super(CustomResponseStatus.VALIDATION_FAILED);
    }

}
