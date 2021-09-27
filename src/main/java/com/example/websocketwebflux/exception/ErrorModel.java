package com.example.websocketwebflux.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class ErrorModel extends RuntimeException{

    @Getter
    private String errorCode;

    @Getter
    private String errorMessage;

    public ErrorModel(CustomResponseStatus customResponseStatus){
        this.errorCode = customResponseStatus.getErrorCode();
        this.errorMessage = customResponseStatus.getErrorMessage();
    };
}
