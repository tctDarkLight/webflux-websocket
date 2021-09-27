package com.example.websocketwebflux.service;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.model.RoomModel;
import reactor.core.publisher.Mono;

public interface RoomService {
    Mono<RoomDTO> createRoom(RoomModel roomModel);
    Mono<Void> deleteRoom(Long roomId);
}
