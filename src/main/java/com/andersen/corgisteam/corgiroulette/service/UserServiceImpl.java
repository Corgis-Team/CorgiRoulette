package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.FieldLengthExceedException;
import com.andersen.corgisteam.corgiroulette.service.exception.RequiredFieldIsEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final TeamService teamService;

    private static final int FIELD_MAX_LENGTH = 100;

    public UserServiceImpl(UserRepository userRepository, TeamService teamService) {
        this.userRepository = userRepository;
        this.teamService = teamService;
    }

    @Override
    public void save(User user) {
        validate(user);
        user.setChosen(false);
        user.setLastDuel(LocalDateTime.now());
        userRepository.save(user);
        log.info("Successfully created user with id {}", user.getId());
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new RequiredFieldIsEmptyException(String.format("Required field is empty. Name: %s", user.getName()));
        }

        if (user.getName().length() > FIELD_MAX_LENGTH) {
            throw new FieldLengthExceedException(String.format("Name length is greater than %d", FIELD_MAX_LENGTH));
        }

        if (user.getSurname() == null || user.getSurname().isBlank()) {
            throw new RequiredFieldIsEmptyException(String.format("Required field is empty. Surname: %s", user.getSurname()));
        }

        if (user.getSurname().length() > FIELD_MAX_LENGTH) {
            throw new FieldLengthExceedException(String.format("Surname length is greater than %d", FIELD_MAX_LENGTH));
        }

        long teamId = user.getTeamId();
        teamService.get(teamId);
    }
}

