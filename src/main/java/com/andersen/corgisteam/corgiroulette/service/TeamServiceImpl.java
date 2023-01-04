package com.andersen.corgisteam.corgiroulette.service;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;

public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void create(Team team) {

    }

    @Override
    public List<Team> getAll() {
        return null;
    }

    @Override
    public Team get(long id) {
        return null;
    }

    @Override
    public void update(Team team) {

    }

    @Override
    public void delete(long id) {

    }
}
