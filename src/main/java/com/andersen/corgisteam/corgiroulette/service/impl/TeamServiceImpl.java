package com.andersen.corgisteam.corgiroulette.service.impl;

import java.util.List;

import com.andersen.corgisteam.corgiroulette.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldContainsNumberException;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldLengthExceedException;
import com.andersen.corgisteam.corgiroulette.service.exception.RequiredFieldIsEmptyException;

public class TeamServiceImpl implements TeamService {

    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private static final int FIELD_MAX_LENGTH = 30;
    private static final String NUMBERS_REGEXP = ".*\\d.*";

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(Team team) {
        validate(team);
        team = teamRepository.save(team);
        log.info("Created team with id {}", team.getId());
    }

    @Override
    public List<Team> getAll() {
        List<Team> teams = teamRepository.findAll();
        log.info("Successfully showed all teams");
        return teams;
    }

    @Override
    public Team get(long id) {
        Team team = teamRepository.findById(id);
        team.setUserList(userRepository.findAllByTeamId(id));
        log.info("Team with id {} was found", id);
        return team;
    }

    @Override
    public List<Team> getAllByName(String name) {
        if (name == null || name.isBlank()) {
            throw new RequiredFieldIsEmptyException("Name field is required");
        }

        List<Team> teams = teamRepository.findByName(name);
        log.info("Teams with name like {} were found", name);
        return teams;
    }

    @Override
    public void update(Team team) {
        Team oldTeam = teamRepository.findById(team.getId());
        validate(team);
        teamRepository.update(team);
        log.info("Updated team with id {}", oldTeam.getId());
    }

    @Override
    public void delete(long id) {
        teamRepository.delete(id);
        log.info("Team with id {} was successfully deleted", id);
    }

    private void validate(Team team) {
        if (team.getName() == null || team.getName().isBlank()) {
            throw new RequiredFieldIsEmptyException(String.format("Required field is empty. Name: %s", team.getName()));
        }

        if (team.getName().length() > FIELD_MAX_LENGTH) {
            throw new FieldLengthExceedException(String.format("Name length is greater than %d", FIELD_MAX_LENGTH));
        }

        if (team.getName().matches(NUMBERS_REGEXP)) {
            throw new FieldContainsNumberException(String.format("Required field is contains numbers. Name: %s",
                team.getName()));
        }
    }
}
