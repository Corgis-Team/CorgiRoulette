package com.andersen.corgisteam.corgiroulette.repository;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.entity.User;

public interface UserRepository {

    void save(User user);

    void update(User user);

    List<User> findAll();

    User findById(long id);

    List<User> findAllByFullName(String fullName);

    List<User> findAllByTeamId(long teamId);

    void handlePair(long userId, long opponentId);

    void refresh();

    void delete(long id);
}
