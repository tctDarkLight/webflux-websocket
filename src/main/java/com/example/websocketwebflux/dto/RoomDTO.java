package com.example.websocketwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO{
    private String name;
    private Long typeGameId;
    private String imageBackgroundUrl;

}
