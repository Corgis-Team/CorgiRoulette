package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.entity.User;

public interface UserRepository {

    void save(User user);

    void update(User user);
}
