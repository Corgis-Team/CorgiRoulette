package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.entity.Team;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.PairRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.NullListForGeneratePairException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FindOpponentsUsingListTest {

    private PairRepository pairRepository;
    private UserService userService;

    private FindOpponentsUsingList findOpponentsUsingList;

    @BeforeEach
    void setUp() {
        pairRepository = Mockito.mock(PairRepository.class);
        userService = Mockito.mock(UserService.class);
        findOpponentsUsingList = new FindOpponentsUsingList(userService, pairRepository);
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
}