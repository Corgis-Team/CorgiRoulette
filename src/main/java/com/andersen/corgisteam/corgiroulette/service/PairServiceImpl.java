package com.andersen.corgisteam.corgiroulette.service;


import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.PairRepository;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.NotAvailablePairsException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PairServiceImpl implements PairService {

    private static final Random RANDOM = new Random();
    private static final int INTERVAL = 7;

    private final PairRepository pairRepository;
    private final UserRepository userRepository;

    public PairServiceImpl(PairRepository pairRepository, UserRepository userRepository) {
        this.pairRepository = pairRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Pair getPair() {
        List<Pair> pairs = pairRepository.findAll();
        Set<Integer> tries = new HashSet<>();
        while (tries.size() != pairs.size()) {
            int anotherTry = RANDOM.nextInt(pairs.size());
            Pair pair = pairs.get(anotherTry);
            User user = userRepository.findById(pair.getUser().getId());
            User opponent = userRepository.findById(pair.getOpponent().getId());
            if (validatePair(user, opponent)) {
                userRepository.handlePair(pair.getUser().getId(), pair.getOpponent().getId());
                return pair;
            }
            tries.add(anotherTry);
        }
        throw new NotAvailablePairsException("All users already answered today");
    }

    @Override
    public void refresh() {
        userRepository.refresh();
    }

    @Override
    public boolean validatePair(User user, User opponent) {
        Duration duration = Duration.between(user.getLastDuel(), LocalDateTime.now());
        long durationInDays1 = duration.toDays();
        Duration durationOpponent = Duration.between(opponent.getLastDuel(), LocalDateTime.now());
        long durationInDays2 = durationOpponent.toDays();

        boolean isUserAvailable = false;
        if (!user.isChosen()) {
            isUserAvailable = true;
        } else if (user.isChosen() && durationInDays1 >= INTERVAL) {
            isUserAvailable = true;
        }

        boolean isOpponentAvailable = false;
        if (!opponent.isChosen()) {
            isOpponentAvailable = true;
        } else if (opponent.isChosen() && durationInDays2 >= INTERVAL) {
            isOpponentAvailable = true;
        }

        return isUserAvailable && isOpponentAvailable;
    }
}