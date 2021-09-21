package com.example.websocketwebflux.service;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.UserModel;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserDTO> createUser(UserModel userModelEntity);
}
