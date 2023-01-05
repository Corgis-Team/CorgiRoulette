package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    List<User> findAll();
}
