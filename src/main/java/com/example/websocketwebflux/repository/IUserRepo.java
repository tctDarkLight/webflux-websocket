package com.example.websocketwebflux.repository;

import com.example.websocketwebflux.model.UserModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IUserRepo extends R2dbcRepository<UserModel, Long> {
    Mono<UserModel> findByUsername(String username);
}
