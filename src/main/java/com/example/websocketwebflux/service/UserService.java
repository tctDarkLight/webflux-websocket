package com.example.websocketwebflux.service;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.UserModel;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDTO> createUser(UserModel userModelEntity);
    Mono<UserDTO> findUserByEmail(String email);
    Mono<Boolean> isExistUser(String email);
    Mono<UserDTO> createFirebaseUser(String firebaseToken);
}
