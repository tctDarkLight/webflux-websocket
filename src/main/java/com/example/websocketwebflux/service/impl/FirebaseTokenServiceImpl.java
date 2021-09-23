package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.service.FirebaseTokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseTokenServiceImpl implements FirebaseTokenService {
    @Override
    public String getUidFromFirebaseToken(String firebaseToken) {
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        if (decodedToken != null && !decodedToken.toString().isBlank())
            return decodedToken.getUid();
        else
            return null;
    }

}
