package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.exception.InvalidFirebaseTokenException;
import com.example.websocketwebflux.model.*;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.FirebaseTokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

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
            throw new InvalidFirebaseTokenException();
        }
        return decodedToken.getUid();

    }

    @Override
    public AuthResponse generateJWTFromUsername(String username) {

        CustomUserDetails userDetails =
            new CustomUserDetails
                (
                    UserModel.builder()
                        .username(username)
                        .role(RoleUser.userRole)
                        .build()
                );

        String token = jwtUtil.generateToken(userDetails);
        Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
        return AuthResponse.builder().token(token).expiredDate(expiredDate).build();

    }

    @Override
    public String getEmailFromFirebaseToken(String firebaseToken) {

        FirebaseToken decodedToken;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new InvalidFirebaseTokenException();
        }
        return decodedToken.getEmail();

    }

    @Override
    public FirebaseUser getFirebaseUserFromFirebaseToken(String firebaseToken) {

        FirebaseToken decodedToken;

        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new InvalidFirebaseTokenException();
        }

        return FirebaseUser.builder()
            .uid(decodedToken.getUid())
            .name(decodedToken.getName())
            .email(decodedToken.getEmail())
            .picture(decodedToken.getPicture())
            .build();

    }

    @Override
    public AuthResponse generateJWTFromFirebaseToken(String firebaseToken) {
        return null;
    }

    @Override
    public AuthResponse generateJWTFromUidAndFirebaseToken(Long uid, String firebaseToken) {
        String email = getEmailFromFirebaseToken(firebaseToken);
        String[] splitEmail = email.split("@");
        String username = splitEmail[0];
        CustomUserDetails userDetails =
            new CustomUserDetails
                (
                    UserModel.builder()
                        .username(username)
                        .id(uid)
                        .role(RoleUser.userRole)
                        .build()
                );

        String token = jwtUtil.generateToken(userDetails);
        Date expiredDate = jwtUtil.getExpirationDateFromToken(token);
        return AuthResponse.builder().token(token).expiredDate(expiredDate).build();
    }
}
