package com.andersen.corgisteam.corgiroulette.mapper;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;

import java.util.List;

public interface UserMapper {
    UserDto userEntityToDto(User user);

    User userDtoToEntity(UserDto userDto);

    List<UserDto> userEntitiesToDtos(List<User> users);
}
