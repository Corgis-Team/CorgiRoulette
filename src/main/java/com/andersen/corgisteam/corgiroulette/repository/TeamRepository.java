package com.andersen.corgisteam.corgiroulette.repository;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.entity.Team;

public interface TeamRepository {

    Team save(Team team);

    List<Team> findAll();

    Team findById(long id);

    List<Team> findByName(String name);

    void update(Team team);

    void delete(long id);
}
