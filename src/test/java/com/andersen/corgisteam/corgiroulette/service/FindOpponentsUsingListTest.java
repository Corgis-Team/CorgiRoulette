package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.database.DatabaseConfig;
import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.*;
import com.andersen.corgisteam.corgiroulette.service.exception.NullListForGeneratePairException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FindOpponentsUsingListTest {
    private static final Logger log = LoggerFactory.getLogger(FindOpponentsUsingListTest.class);
    private PairRepository pairRepository;
    private UserService userService;

    private FindOpponentsUsingList findOpponentsUsingList;
    Connection connection;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        pairRepository = Mockito.mock(PairRepository.class);
        userService = Mockito.mock(UserService.class);
        findOpponentsUsingList = new FindOpponentsUsingList(userService, pairRepository);

        try {
            this.connection = DatabaseConfig.getConnection();
            this.userRepository = new UserRepositoryImpl(new TeamRepositoryImpl());
        } catch (SQLException e) {
            throw new RuntimeException("Can't get database connection");
        }
    }


    @Test
    void createEmptyListAndCheckRandom() {
        List<User> users = new ArrayList<>();
        Assertions.assertThrows(NullListForGeneratePairException.class, () -> FindOpponentsUsingList.getRandomElement(users));
    }

    @Test
    void createNotEmptyListAndCheckForEmpty() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        Assertions.assertEquals(users, findOpponentsUsingList.checkForEmpty(users));
    }

    @Test
    void createEmptyListAndCheckForEmpty() {
        List<User> users = new ArrayList<>();
        Assertions.assertNotEquals(null, findOpponentsUsingList.checkForEmpty(users));
    }

    @Test
    void createListWithPeopleFromTheSameTeamAndCheckForOddAndTeammates() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Polina", "Belkevich", new Team(1, "Red"), false, LocalDateTime.now()));
        users.add(new User(2, "Vladislav", "Nemiro", new Team(1, "Red"), false, LocalDateTime.now()));

        List<User> usersTeammates = List.copyOf(users);
        Assertions.assertNotEquals(usersTeammates, findOpponentsUsingList.checkForOddAndTeammates(users));
    }

    @Test
    void createListWithPeopleFromOtherTeamsAndCheckForOddAndTeammates() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Polina", "Belkevich", new Team(1, "Red"), false, LocalDateTime.now()));
        users.add(new User(2, "Vladislav", "Nemiro", new Team(2, "Red"), false, LocalDateTime.now()));

        List<User> usersTeammates = List.copyOf(users);
        Assertions.assertEquals(usersTeammates, findOpponentsUsingList.checkForOddAndTeammates(users));
    }

    @Test
    void createListWithOnePersonAndCheckForOddAndTeammates() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Polina", "Belkevich", new Team(1, "Red"), false, LocalDateTime.now()));

        List<User> usersTeammates = List.copyOf(users);
        Assertions.assertNotEquals(usersTeammates, findOpponentsUsingList.checkForOddAndTeammates(users));
    }

    @Test
    void createListAndCheckCountOfOpponents() {
        List<User> originalUsersNotPicked = new ArrayList<>();
        originalUsersNotPicked.add(new User(1, "Polina", "Belkevich", new Team(1, "Red"), false, LocalDateTime.now()));
        originalUsersNotPicked.add(new User(2, "Vladislav", "Nemiro", new Team(2, "Red"), false, LocalDateTime.now()));

        List<User> users = new ArrayList<>();
        users.add(new User(1, "Polina", "Belkevich", new Team(1, "Red"), false, LocalDateTime.now()));
        users.add(new User(2, "Vladislav", "Nemiro", new Team(2, "Red"), false, LocalDateTime.now()));

        List<User> usersTeammates = List.copyOf(users);
        Assertions.assertEquals(usersTeammates, findOpponentsUsingList.checkCountOfOpponents(originalUsersNotPicked.get(0), users, originalUsersNotPicked));
    }

    @Test
    void createEmptyListAndCheckCountOfOpponents() {
        List<User> originalUsersNotPicked = new ArrayList<>();
        originalUsersNotPicked.add(new User(1, "Polina", "Belkevich", new Team(1, "Red"), false, LocalDateTime.now()));
        originalUsersNotPicked.add(new User(2, "Vladislav", "Nemiro", new Team(2, "Red"), false, LocalDateTime.now()));

        List<User> users = new ArrayList<>();

        List<User> usersTeammates = List.copyOf(users);
        Assertions.assertNotEquals(usersTeammates, findOpponentsUsingList.checkCountOfOpponents(originalUsersNotPicked.get(0), users, originalUsersNotPicked));
    }

    @Test
    void createAllPairs() {
        List <User> allUsers = userRepository.findAll();
        System.out.println(allUsers);
        for (int i = 0; i < (int)Math.ceil(allUsers.size()/2.0); i++) {
            Pair pair = findOpponentsUsingList.createOpponents();
            log.info("Create pair for user id {}", pair.getUser().getId());
            log.info("Create pair for opponent id {}", pair.getOpponent().getId());
            Assertions.assertEquals(pair, findOpponentsUsingList.createPairOfOpponents(pair.getUser(),pair.getUser()));
        }
    }

    @AfterEach
    public void after() throws SQLException {
        this.connection.close();
    }
}