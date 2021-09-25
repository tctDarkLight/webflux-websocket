package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.exception.InvalidFirebaseTokenException;
import com.example.websocketwebflux.exception.CustomResponseStatus;
import com.example.websocketwebflux.model.AuthResponse;
import com.example.websocketwebflux.model.CustomUserDetails;
import com.example.websocketwebflux.model.UserModel;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.FirebaseTokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class FirebaseTokenServiceImpl implements FirebaseTokenService {

    private final JWTUtil jwtUtil;

    public FirebaseTokenServiceImpl(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String getUidFromFirebaseToken(String firebaseToken) {
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new InvalidFirebaseTokenException(CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorCode(), CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorMessage());
        }
        return decodedToken.getUid();
    }

    @Override
    public AuthResponse generateJWTFromUsername(String uid) {
        CustomUserDetails userDetails = new CustomUserDetails(UserModel.builder().username(uid).role("USER").build());
        String token = jwtUtil.generateToken(userDetails);
        Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
        return AuthResponse.builder().token(token).expiredDate(expiredDate).build();
    }

    @Override
    public String getEmailFromFirebaseToken(String firebaseToken) {
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new InvalidFirebaseTokenException(CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorCode(), CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorMessage());
        }
        return decodedToken.getEmail();
        /*String[] splitEmail = email.split("@");
        return splitEmail[0];*/
    }

}
