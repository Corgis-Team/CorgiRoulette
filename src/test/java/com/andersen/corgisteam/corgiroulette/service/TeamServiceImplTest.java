package com.andersen.corgisteam.corgiroulette.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.repository.TeamRepository;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldLengthExceedException;
import com.andersen.corgisteam.corgiroulette.service.exception.RequiredFieldIsEmptyException;

class TeamServiceImplTest {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamRepository = Mockito.mock(TeamRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        teamService = new TeamServiceImpl(teamRepository, userRepository);
    }

    @Test
    void create() {
        Team team = new Team("Blue");
        Team createdTeam = new Team(1, "Blue");

        Mockito.when(teamRepository.save(team)).thenReturn(createdTeam);
        teamService.create(team);

        Mockito.verify(teamRepository).save(team);
    }

    @Test
    void createTeamWithEmptyName() {
        Team team = new Team("");
        Assertions.assertThrows(RequiredFieldIsEmptyException.class, () -> teamService.create(team));
    }

    @Test
    void createTeamWithLargeName() {
        Team team = new Team("jfaufhavkcjfiajflkdakvjioajffvmdffj");
        Assertions.assertThrows(FieldLengthExceedException.class, () -> teamService.create(team));
    }
}