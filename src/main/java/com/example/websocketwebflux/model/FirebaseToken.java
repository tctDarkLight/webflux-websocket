package com.example.websocketwebflux.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FirebaseToken {

    @NotEmpty
    private String firebaseToken;

}
