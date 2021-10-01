package com.example.websocketwebflux.exception;

public class CreateRoomFailException extends ErrorModel{
    public CreateRoomFailException() {
        super(CustomResponseStatus.CREATE_ROOM_FAIL);
    }
}
