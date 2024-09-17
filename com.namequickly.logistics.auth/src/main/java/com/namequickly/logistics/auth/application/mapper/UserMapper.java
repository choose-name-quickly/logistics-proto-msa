package com.namequickly.logistics.auth.application.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.auth.application.dto.UserDto;
import com.namequickly.logistics.auth.application.dto.UserInfoResponseDto;
import com.namequickly.logistics.auth.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    // entity to Dto
    UserInfoResponseDto userToUserInfoResponseDto(User user);

    // entity to Dto
    UserDto userToUserDto(User user);

    // Dto to entity
    User userDtoToUser(UserDto userDto);
}