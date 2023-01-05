package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findById(long id);

    List<User> findAllByFullName(String fullName);

    List<User> findAllByTeamId(long teamId);
}
