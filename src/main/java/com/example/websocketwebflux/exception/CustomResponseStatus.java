package com.example.websocketwebflux.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum CustomResponseStatus {

    INVALID_FIREBASE_TOKEN("ER_0001", "Invalid firebase token"),
    VALIDATION_FAILED("ER_0002", "Validation failed"),
    USER_NOT_FOUND("ER_0003", "User not found"),
    CREATE_ROOM_FAIL("ER_0004", "Create room fail");

    @Getter
    private String errorCode;

    @Getter
    private String errorMessage;
}
