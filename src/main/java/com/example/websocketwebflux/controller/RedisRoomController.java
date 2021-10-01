package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.model.BaseResponse;
import com.example.websocketwebflux.model.RoomModel;
import com.example.websocketwebflux.service.RoomRedisService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class RedisRoomController {

    private final RoomRedisService roomRedisService;

    public RedisRoomController(RoomRedisService roomRedisService) {
        this.roomRedisService = roomRedisService;
    }

    @PostMapping("/redis-room")
    public Mono<BaseResponse<Object>> createRoom(Authentication authentication, @RequestBody RoomModel roomModel) {
        return roomRedisService.saveRoom(authentication, roomModel);
    }

    @GetMapping("/redis-room/{index}")
    public Mono<BaseResponse<Object>> getRoom(@PathVariable("index") Long index) {
        return roomRedisService.getRoom(index);
    }

    @DeleteMapping("/redis-room/{index}")
    public Mono<BaseResponse<Object>> delete(@PathVariable("index") Long index) {
        return roomRedisService.deleteRoom(index);
    }

    @DeleteMapping("/redis-room")
    public Mono<BaseResponse<Object>> deleteAll(){
        return roomRedisService.deleteAll();
    }
}
