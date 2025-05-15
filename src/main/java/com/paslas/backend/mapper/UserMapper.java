package com.paslas.backend.mapper;

import com.paslas.backend.dto.UserResponseDto;
import com.paslas.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto userToUserResponseDto(User user);
}
