package com.example.websocketwebflux.service.impl;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.mapper.RoomMapper;
import com.example.websocketwebflux.model.BaseResponse;
import com.example.websocketwebflux.model.RoomModel;
import com.example.websocketwebflux.service.RoomRedisService;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class RoomRedisServiceImpl implements RoomRedisService {

    private final ReactiveRedisTemplate<String, RoomModel> reactiveRedisTemplate;
    private final RoomMapper roomMapper;
    private static String KEY = "rooms";

    public RoomRedisServiceImpl(ReactiveRedisTemplate<String, RoomModel> reactiveRedisTemplate, RoomMapper roomMapper) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.roomMapper = roomMapper;
    }

    @Override
    public Mono<BaseResponse<Object>> saveRoom(Authentication authentication, RoomModel room) {

        Long uid = (Long) authentication.getPrincipal();
        room.setIdCreator(uid);
        room.setCreateTime(new Date());
        return reactiveRedisTemplate.opsForList()
            .rightPush("rooms", room)
            .flatMap(index -> reactiveRedisTemplate.opsForList().index(KEY, index).map(roomMapper::toDTO))
            .map(roomDTO -> BaseResponse.builder().result(roomDTO).code(HttpStatus.OK.name()).build());
    }

    @Override
    public Mono<BaseResponse<Object>> getRoom(Long index) {
        return reactiveRedisTemplate.opsForList()
            .index("rooms", index)
            .map(roomMapper::toDTO)
            .map(roomDTO -> BaseResponse.builder().result(roomDTO).code(HttpStatus.OK.name()).build());
    }

    @Override
    public Mono<BaseResponse<Object>> deleteRoom(Long index) {
        return reactiveRedisTemplate.opsForList()
            .index("rooms", index)
            .flatMap(room -> reactiveRedisTemplate.opsForList().remove(KEY, 0, room))
            .map(number -> BaseResponse.builder().result(true).code(HttpStatus.OK.name()).build());
    }

    @Override
    public Mono<BaseResponse<Object>> deleteAll() {
        return reactiveRedisTemplate.opsForList()
            .delete(KEY)
            .map(result  -> BaseResponse.builder().result(result).code(HttpStatus.OK.name()).build());
    }
}
