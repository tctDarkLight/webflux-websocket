package com.example.websocketwebflux.service;

import com.example.websocketwebflux.model.BaseResponse;
import com.example.websocketwebflux.model.FirebaseToken;
import reactor.core.publisher.Mono;

public interface AuthService {
    String getUsernameFromToken(String token);
    Mono<BaseResponse<Object>> loginFirebase(FirebaseToken firebaseToken);
    Mono<BaseResponse<Object>> signUpFirebase(FirebaseToken firebaseToken);
}
