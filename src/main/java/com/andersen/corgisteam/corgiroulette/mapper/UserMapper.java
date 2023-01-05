package com.andersen.corgisteam.corgiroulette.mapper;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;

import java.util.List;

public interface UserMapper {
    UserDto userEntityToModel(User user);

    User userModelToEntity(UserDto userDto);

    List<UserDto> userEntitiesToModels(List<User> users);
}
