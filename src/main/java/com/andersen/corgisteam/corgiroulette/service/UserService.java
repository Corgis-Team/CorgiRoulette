package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.UserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;

import java.util.List;

import java.util.List;

public interface UserService {

    void save(UserDto user);
    
    void update(UserDto user);

    List<User> getAll();

    UserDto get(long id);

    List<UserDto> getAllByFullName(String fullName);

    List<UserDto> getAllByTeamId(long teamId);
}
