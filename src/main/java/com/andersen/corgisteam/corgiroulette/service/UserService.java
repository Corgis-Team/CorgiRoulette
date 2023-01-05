package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;

import java.util.List;

public interface UserService {

    void save(UserDto user);

    UserDto get(long id);

    List<UserDto> getAllByFullName(String fullName);

    List<UserDto> getAllByTeamId(long teamId);
}
