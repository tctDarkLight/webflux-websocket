package com.example.websocketwebflux.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum CustomResponseStatus {

    INVALID_FIREBASE_TOKEN("ER_0001", "Invalid firebase token");

    @Getter
    private String errorCode;
    @Getter
    private String errorMessage;
}
