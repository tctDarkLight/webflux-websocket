package com.example.websocketwebflux.exception;

public class InvalidFirebaseTokenException extends ErrorModel{

    public InvalidFirebaseTokenException() {
        super(CustomResponseStatus.INVALID_FIREBASE_TOKEN);
    }

}
