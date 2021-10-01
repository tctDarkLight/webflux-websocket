package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.exception.UserNotFoundException;
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

        String email = firebaseTokenService.getEmailFromFirebaseToken(firebaseToken.getFirebaseToken());

        return userService
            .findUserByEmail(email)
            .flatMap(result -> {
                Long uid = result.getId();

                AuthResponse authResponse
                    = firebaseTokenService.generateJWTFromUidAndFirebaseToken(uid, firebaseToken.getFirebaseToken());

                return Mono.just(BaseResponse.builder().result(authResponse).code(HttpStatus.OK.name()).build());
            })
            .switchIfEmpty(Mono.error(new UserNotFoundException()));

    }

    @Override
    public Mono<BaseResponse<Object>> signUpFirebase(FirebaseToken firebaseToken) {

        String email = firebaseTokenService.getEmailFromFirebaseToken(firebaseToken.getFirebaseToken());

        return userService.findUserByEmail(email)
            .map(result -> {
                Long uid = result.getId();

                AuthResponse authResponse =
                    firebaseTokenService.generateJWTFromUidAndFirebaseToken(uid, firebaseToken.getFirebaseToken());

                return BaseResponse.builder().result(authResponse).code(HttpStatus.OK.name()).build();
            })
            .switchIfEmpty
                (
                    userService.createFirebaseUser(firebaseToken.getFirebaseToken())
                        .map(result -> {
                            Long uid = result.getId();

                            AuthResponse authResponse =
                                firebaseTokenService.generateJWTFromUidAndFirebaseToken(
                                    uid, firebaseToken.getFirebaseToken()
                                );

                            return BaseResponse.builder().result(authResponse).code(HttpStatus.OK.name()).build();
                        })
                );
    }

}
