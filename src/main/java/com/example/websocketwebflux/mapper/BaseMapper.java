package com.example.websocketwebflux.mapper;

import com.example.websocketwebflux.dto.BaseDTO;
import com.example.websocketwebflux.model.BaseModel;

import java.util.List;

public interface BaseMapper<DTO extends BaseDTO, ENTITY extends BaseModel> {

    DTO toDTO(ENTITY entity);

    ENTITY toEntity(DTO dto);

    List<DTO> toDTO(List<ENTITY> entities);

    List<ENTITY> toEntity(List<DTO> dtos);

}
