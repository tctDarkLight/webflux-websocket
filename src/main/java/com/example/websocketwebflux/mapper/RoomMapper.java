package com.example.websocketwebflux.mapper;

import com.example.websocketwebflux.dto.RoomDTO;
import com.example.websocketwebflux.model.RoomModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper extends BaseMapper<RoomDTO, RoomModel> {
}
