package com.andersen.corgisteam.corgiroulette.service;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.dto.RequestUserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;

public interface UserService {

    void save(RequestUserDto user);

    void update(RequestUserDto user);

    List<User> getAll();

    User get(long id);

    List<User> getAllByFullName(String fullName);

    List<User> getAllByTeamId(long teamId);
}
