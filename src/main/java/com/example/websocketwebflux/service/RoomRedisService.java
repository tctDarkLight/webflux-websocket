package com.example.websocketwebflux.service;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.model.BaseResponse;
import com.example.websocketwebflux.model.RoomModel;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface RoomRedisService {

    Mono<BaseResponse<Object>> saveRoom(Authentication authentication, RoomModel user);

    Mono<BaseResponse<Object>> getRoom(Long index);

    Mono<BaseResponse<Object>> deleteRoom(Long index);

    Mono<BaseResponse<Object>> deleteAll();
}
