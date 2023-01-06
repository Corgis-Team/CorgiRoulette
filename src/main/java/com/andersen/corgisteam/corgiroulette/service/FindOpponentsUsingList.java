package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.PairRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.NotAvailablePairsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindOpponentsUsingList {
    private static final Logger log = LoggerFactory.getLogger(FindOpponentsUsingList.class);
    private final UserService userService;
    private final PairRepository pairRepository;

    public FindOpponentsUsingList(UserService userService, PairRepository pairRepository) {
        this.userService = userService;
        this.pairRepository = pairRepository;
    }

    public void createPairInBattleTable(long userId, long opponentId) {
        pairRepository.createPairInBattleTable(userId, opponentId);
        log.info("Successfully created new pair with for user with id {}", userId);
        log.info("The opponent with id {}", opponentId);
    }

    public void deleteUserOpponent(long userId) {
        pairRepository.deleteUserOpponent(userId);
        log.info("Successfully deleted pairs with id {}", userId);
    }

    public List<User> createListWithoutPicked() {
        return userService.getUsersWhereIsChosenFalse();
    }

    public List<User> checkForEmpty(List<User> users) {
        if (users.isEmpty()) {
            userService.changeStatusForAllUsers();
            users = userService.getUsersWhereIsChosenFalse();
        }
        return users;
    }

    public List<User> checkForOdd(List<User> users) {
        if (users.size() == 1) {
            System.out.println(users.get(0).getName() + " is lucky!");
            userService.updateStatusChosenUser(users.get(0).getId());
            throw new NotAvailablePairsException("All users already answered today");
        }
        return users;
    }

    public List<User> deleteToChooseSuitableOpponents(User user, List<User> users) {
        List<User> suitableOpponentsUsers = new ArrayList<>(users);
        suitableOpponentsUsers.removeIf(newUser -> newUser.getTeam().getId() == user.getTeam().getId());

        List<User> opponentUsers = userService.getUsersWhichWereOpponentsBefore(user.getId());
        suitableOpponentsUsers.removeAll(opponentUsers);
        return suitableOpponentsUsers;
    }

    public List<User> checkCountOfOpponents(User user, List<User> usersOpponents, List<User> usersHaveNotPicked) {
        if (usersOpponents.isEmpty()) {
            deleteUserOpponent(user.getId());

            for (User newUser : usersHaveNotPicked) {
                if (newUser.getTeam().getId() != user.getTeam().getId()) {
                    usersOpponents.add(newUser);
                }
            }
        }
        return usersOpponents;
    }

    public Pair createPairOfOpponents(User chosenUser, User opponentForUser) {
        userService.updateStatusChosenUser(chosenUser.getId());
        userService.updateStatusChosenUser(opponentForUser.getId());

        createPairInBattleTable(chosenUser.getId(), opponentForUser.getId());
        return new Pair(chosenUser.getId(), opponentForUser.getId());
    }

    public static User getRandomElement(List<User> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }


    public Pair createOpponents() {
        List<User> originalUsersNotPicked = createListWithoutPicked();

        originalUsersNotPicked = checkForEmpty(originalUsersNotPicked);
        User userChosen = getRandomElement(originalUsersNotPicked);
        originalUsersNotPicked = checkForOdd(originalUsersNotPicked);

        List<User> suitableOpponentsUsers = deleteToChooseSuitableOpponents(userChosen, originalUsersNotPicked);
        suitableOpponentsUsers = checkCountOfOpponents(userChosen, suitableOpponentsUsers, originalUsersNotPicked);
        User opponentUser = getRandomElement(suitableOpponentsUsers);

        return createPairOfOpponents(userChosen, opponentUser);
    }
}