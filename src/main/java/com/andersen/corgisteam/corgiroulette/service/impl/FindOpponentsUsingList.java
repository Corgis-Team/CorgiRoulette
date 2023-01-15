package com.andersen.corgisteam.corgiroulette.service.impl;

import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.PairRepository;
import com.andersen.corgisteam.corgiroulette.service.PairService;
import com.andersen.corgisteam.corgiroulette.service.UserService;
import com.andersen.corgisteam.corgiroulette.service.exception.NullListForGeneratePairException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FindOpponentsUsingList implements PairService {
    private static final Logger log = LoggerFactory.getLogger(FindOpponentsUsingList.class);
    private final UserService userService;
    private final PairRepository pairRepository;

    public FindOpponentsUsingList(UserService userService, PairRepository pairRepository) {
        this.userService = userService;
        this.pairRepository = pairRepository;
    }

    @Override
    public Pair getPair() {
        List<User> originalUsersNotPicked = getUsersNotPicked();
        User userChosen = getRandomElement(originalUsersNotPicked);
        User opponentUser = getOpponent(userChosen, originalUsersNotPicked);

        return createPairOfOpponents(userChosen, opponentUser);
    }

    @Override
    public Pair changeOpponent(User userToSave, User userToChange) {
        Pair pair = new Pair(userToSave, userToChange);
        deletePair(pair);

        List<User> originalUsersNotPicked = getUsersNotPicked();
        pair.setOpponent(getOpponent(userToSave, originalUsersNotPicked));

        return createPairOfOpponents(pair);
    }

    private List<User> getUsersNotPicked() {
        List<User> originalUsersNotPicked = userService.getUsersWhereIsChosenFalse();
        originalUsersNotPicked = checkForEmpty(originalUsersNotPicked);
        originalUsersNotPicked = checkForOddAndTeammates(originalUsersNotPicked);

        return originalUsersNotPicked;
    }

    public List<User> checkForEmpty(List<User> users) {
        if (users.isEmpty()) {
            userService.refreshUsers();
            users = userService.getUsersWhereIsChosenFalse();
        }
        return users;
    }

    public List<User> checkForOddAndTeammates(List<User> users) {
        Set<Long> teamsIDs = new HashSet<>();
        for (User newUser : users) {
            teamsIDs.add(newUser.getTeam().getId());
        }

        if (teamsIDs.size() <= 1) {
            userService.refreshUsers();
            users = userService.getUsersWhereIsChosenFalse();
        }
        return users;
    }

    private User getOpponent(User userToSave, List<User> originalUsersNotPicked) {
        List<User> suitableOpponentsUsers = deleteToChooseSuitableOpponents(userToSave, originalUsersNotPicked);
        suitableOpponentsUsers = checkCountOfOpponents(userToSave, suitableOpponentsUsers, originalUsersNotPicked);
        return getRandomElement(suitableOpponentsUsers);
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

    public void deleteUserOpponent(long userId) {
        pairRepository.deleteUserOpponent(userId);
        log.info("Successfully deleted pairs with id {}", userId);
    }

    public void deletePair(Pair pair) {
        pairRepository.deletePair(pair);
        log.info("Successfully deleted pair with users id {} and {}",
                pair.getUser().getId(), pair.getOpponent().getId());
    }

    public Pair createPairOfOpponents(User chosenUser, User opponentForUser) {
        userService.updateStatusChosenUser(chosenUser.getId());
        userService.updateStatusChosenUser(opponentForUser.getId());

        createPairInBattleTable(chosenUser.getId(), opponentForUser.getId());
        return new Pair(chosenUser, opponentForUser);
    }

    public Pair createPairOfOpponents(Pair pair) {
        userService.updateStatusChosenUser(pair.getOpponent().getId());
        createPairInBattleTable(pair.getUser().getId(), pair.getOpponent().getId());
        return pair;
    }

    public void createPairInBattleTable(long userId, long opponentId) {
        pairRepository.createPairInBattleTable(userId, opponentId);
        log.info("Successfully created new pair for user with id {}", userId);
        log.info("The opponent with id {}", opponentId);
    }

    public static User getRandomElement(List<User> list) {
        if (list.isEmpty()) {
            throw new NullListForGeneratePairException("List for random shouldn't be null");
        }
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
