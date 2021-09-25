package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.exception.CustomResponseStatus;
import com.example.websocketwebflux.exception.InvalidFirebaseTokenException;
import com.example.websocketwebflux.model.AuthResponse;
import com.example.websocketwebflux.model.BaseResponse;
import com.example.websocketwebflux.model.FirebaseToken;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.AuthService;
import com.example.websocketwebflux.service.FirebaseTokenService;
import com.example.websocketwebflux.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

    private final JWTUtil jwtUtil;
    private final FirebaseTokenService firebaseTokenService;
    private final UserService userService;

    public AuthServiceImpl(JWTUtil jwtUtil, FirebaseTokenService firebaseTokenService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.firebaseTokenService = firebaseTokenService;
        this.userService = userService;
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtUtil.getUsernameFromToken(token);
    }

    @Override
    public Mono<BaseResponse<Object>> loginFirebase(FirebaseToken firebaseToken) {

        String email = "";
        try {
            email = firebaseTokenService.getEmailFromFirebaseToken(firebaseToken.getFirebaseToken());
        }catch (InvalidFirebaseTokenException ex){
            return Mono.error(new InvalidFirebaseTokenException(CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorCode(), CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorMessage()));
        }
        String finalEmail = email;
        return userService
            .findUserByEmail(finalEmail)
            .flatMap(result -> {
                String[] splitEmail = finalEmail.split("@");
                AuthResponse authResponse = firebaseTokenService.generateJWTFromUsername(splitEmail[0]);
                return Mono.just(BaseResponse.builder().result(authResponse).code(HttpStatus.OK.name()).build());
            })
            .switchIfEmpty(Mono.error(new InvalidFirebaseTokenException(CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorCode(), CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorMessage())));
    }

    @Override
    public Mono<BaseResponse<Object>> signUpFirebase(FirebaseToken firebaseToken) {
        String email = "";
        try {
            email = firebaseTokenService.getEmailFromFirebaseToken(firebaseToken.getFirebaseToken());
        }catch (InvalidFirebaseTokenException ex){
            return Mono.error(new InvalidFirebaseTokenException(CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorCode(), CustomResponseStatus.INVALID_FIREBASE_TOKEN.getErrorMessage()));
        }
        String finalEmail = email;
        String[] splitEmail = finalEmail.split("@");
        return userService.findUserByEmail(finalEmail)
            .flatMap(result -> {
                AuthResponse authResponse = firebaseTokenService.generateJWTFromUsername(splitEmail[0]);
                return Mono.just(BaseResponse.builder().result(authResponse).code(HttpStatus.OK.name()).build());
            })
            .switchIfEmpty(Mono.defer(() -> {
                    return userService.createFirebaseUser(firebaseToken.getFirebaseToken())
                        .flatMap(result -> {
                            AuthResponse authResponse = firebaseTokenService.generateJWTFromUsername(splitEmail[0]);
                            return Mono.just(BaseResponse.builder().result(authResponse).code(HttpStatus.OK.name()).build());
                        });
                })
            );
    }
}
