package com.example.websocketwebflux.service;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.model.RoomModel;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface RoomService {
    Mono<RoomDTO> createRoom(Authentication authentication, RoomModel roomModel);
    Mono<RoomDTO> createRoomWithToken(String authToken, RoomModel roomModel);
    Mono<Void> deleteRoom(Long roomId);
}
