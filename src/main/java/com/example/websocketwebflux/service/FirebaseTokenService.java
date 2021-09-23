package com.example.websocketwebflux.service;

import com.example.websocketwebflux.model.CustomUserDetails;

public interface FirebaseTokenService {
    String getUidFromFirebaseToken(String token);
}
