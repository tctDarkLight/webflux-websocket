package com.example.websocketwebflux.controller;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.model.RoomModel;
import com.example.websocketwebflux.service.RoomService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
//@RequestMapping(value = "/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(value = "/room")
    public Mono<RoomDTO> createRoom(@RequestBody RoomModel roomModel){
        return roomService.createRoom(roomModel);
    }

    @DeleteMapping(value = "/room/{id}")
    public Mono<Void> deleteRoom(@PathVariable("id") Long roomId){
        return roomService.deleteRoom(roomId);
    }

}
