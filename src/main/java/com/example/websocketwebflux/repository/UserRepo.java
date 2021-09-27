package com.example.websocketwebflux.repository;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.UserModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepo extends R2dbcRepository<UserModel, Long> {

    Mono<UserModel> findByUsername(String username);
    Mono<UserModel> findByEmail(String email);

}
