package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.exception.CreateRoomFailException;
import com.example.websocketwebflux.model.RoomModel;
import com.example.websocketwebflux.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
//@RequestMapping(value = "/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /*@PostMapping(value = "/room")
    public Mono<RoomDTO> createRoom(Authentication authentication, @RequestBody RoomModel roomModel){
        return roomService.createRoom(authentication, roomModel);
    }

    @DeleteMapping(value = "/room/{id}")
    public Mono<Void> deleteRoom(@PathVariable("id") Long roomId){
        return roomService.deleteRoom(roomId);
    }*/

}
