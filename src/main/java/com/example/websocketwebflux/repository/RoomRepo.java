package com.example.websocketwebflux.repository;

import com.example.websocketwebflux.model.RoomModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends R2dbcRepository<RoomModel, Long> {
}
