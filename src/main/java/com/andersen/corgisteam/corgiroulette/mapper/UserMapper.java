package com.andersen.corgisteam.corgiroulette.mapper;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.dto.RequestUserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;

public interface UserMapper {

    RequestUserDto userEntityToDto(User user);

    User userDtoToEntity(RequestUserDto requestUserDto);

    List<RequestUserDto> userEntitiesToDtos(List<User> users);
}
