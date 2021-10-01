package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.exception.CreateRoomFailException;
import com.example.websocketwebflux.mapper.RoomMapper;
import com.example.websocketwebflux.model.CustomUserDetails;
import com.example.websocketwebflux.model.RoomModel;
import com.example.websocketwebflux.repository.RoomRepo;
import com.example.websocketwebflux.security.JWTUtil;
import com.example.websocketwebflux.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    private final RoomMapper roomMapper;
    private final JWTUtil jwtUtil;

    public RoomServiceImpl(RoomRepo roomRepo,
                           RoomMapper roomMapper,
                           JWTUtil jwtUtil) {
        this.roomRepo = roomRepo;
        this.roomMapper = roomMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<RoomDTO> createRoom(Authentication authentication, RoomModel roomModel) {

        Long uid = (Long) authentication.getPrincipal();

        roomModel.setCreateTime(new Date());
        roomModel.setIdCreator(uid);
        return roomRepo.save(roomModel)
            .map(roomMapper::toDTO)
            .doOnError(throwable -> {
                throw new CreateRoomFailException();
            });
    }

    @Override
    public Mono<RoomDTO> createRoomWithToken(String authToken, RoomModel roomModel) {
        return null;
    }

    @Override
    public Mono<Void> deleteRoom(Long roomId) {
        return roomRepo.deleteById(roomId);
    }
}
