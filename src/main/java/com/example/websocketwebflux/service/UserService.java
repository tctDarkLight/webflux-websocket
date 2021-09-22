package com.example.websocketwebflux.service;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.UserModel;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDTO> createUser(UserModel userModelEntity);
}
