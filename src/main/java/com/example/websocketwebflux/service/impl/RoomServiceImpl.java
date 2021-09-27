package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.mapper.RoomMapper;
import com.example.websocketwebflux.model.TypeGame;
import com.example.websocketwebflux.model.RoomModel;
import com.example.websocketwebflux.repository.RoomRepo;
import com.example.websocketwebflux.service.RoomService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepo roomRepo, RoomMapper roomMapper) {
        this.roomRepo = roomRepo;
        this.roomMapper = roomMapper;
    }

    @Override
    public Mono<RoomDTO> createRoom(RoomModel roomModel) {

        roomModel.setCreateTime(new Date());
        //roomModel.setRoleGame(TypeGame.concatWord);
        return roomRepo.save(roomModel)
            .map(roomMapper::toDTO);

    }

    @Override
    public Mono<Void> deleteRoom(Long roomId) {
        return roomRepo.deleteById(roomId);
    }
}
