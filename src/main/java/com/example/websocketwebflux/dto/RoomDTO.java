package com.example.websocketwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO extends BaseDTO{

    private String name;
    private String roleGame;
    private String imageBackgroundUrl;

}
