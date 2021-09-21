package com.example.websocketwebflux.mapper;

import com.example.websocketwebflux.dto.UserDTO;
import com.example.websocketwebflux.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper extends BaseMapper<UserDTO, UserModel> {
}
