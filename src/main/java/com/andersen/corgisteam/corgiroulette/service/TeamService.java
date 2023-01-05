package com.andersen.corgisteam.corgiroulette.service;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.entity.Team;

public interface TeamService {

    void create(Team team);

    List<Team> getAll();

    Team get(long id);

    List<Team> getAllByName(String name);

    void update(Team team);

    void delete(long id);
}
