package com.example.websocketwebflux.service;

import com.example.websocketwebflux.model.AuthResponse;
import com.example.websocketwebflux.model.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthException;
import reactor.core.publisher.Mono;

public interface FirebaseTokenService {
    String getUidFromFirebaseToken(String token) throws FirebaseAuthException;

    AuthResponse generateJWTFromUsername(String str);

    AuthResponse generateJWTFromFirebaseToken(String firebaseToken);

    AuthResponse generateJWTFromUidAndFirebaseToken(Long uid, String firebaseToken);

    String getEmailFromFirebaseToken(String token);

    FirebaseUser getFirebaseUserFromFirebaseToken(String firebaseToken);
}
