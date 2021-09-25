package com.example.websocketwebflux.exception;

public class InvalidFirebaseTokenException extends ErrorModel{
    public InvalidFirebaseTokenException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
