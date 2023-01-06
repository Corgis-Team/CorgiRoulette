package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.RequestUserDto;
import com.andersen.corgisteam.corgiroulette.entity.User;

import java.util.List;

public interface UserService {

    void save(RequestUserDto user);

    void update(RequestUserDto user);

    List<User> getAll();

    User get(long id);

    List<User> getAllByFullName(String fullName);

    List<User> getAllByTeamId(long teamId);

    void delete(long id);

}
