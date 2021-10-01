package com.example.websocketwebflux.exception;

public class UserNotFoundException extends ErrorModel{
    public UserNotFoundException() {
        super(CustomResponseStatus.USER_NOT_FOUND);
    }
}
