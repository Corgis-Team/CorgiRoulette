package com.andersen.corgisteam.corgiroulette.service;


import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.PairRepositoryImpl;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.NotAvailablePairsException;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PairGenerator {

    private static final Random RANDOM = new Random();

    private final PairRepositoryImpl pairRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public PairGenerator(UserRepository userRepository, UserService userService) {
        this.pairRepository = new PairRepositoryImpl();
        this.userRepository = userRepository;
        this.userService = userService;
        generatePairs();
    }

    public Pair getPair() {
        List<Pair> pairs = pairRepository.findAll();
        Set<Integer> tries = new HashSet<>();
        while (tries.size() != pairs.size()) {
            int anotherTry = RANDOM.nextInt(pairs.size());
            Pair pair = pairs.get(anotherTry);
            User user = userRepository.findById(pair.getUserId());
            User opponent = userRepository.findById(pair.getOpponentId());
            if (userService.validatePair(user, opponent)) {
                userRepository.handlePair(pair.getUserId(), pair.getOpponentId());
                return pair;
            }
            tries.add(anotherTry);
        }
        throw new NotAvailablePairsException("All users already answered today");
    }

    public void generatePairs() {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            for (int j = users.size() - 1; j > 0; j--) {
                User u1 = users.get(i);
                User u2 = users.get(j);
                if (u1.getTeam().getId() != u2.getTeam().getId()) {
                    Pair pair = new Pair(u1.getId(), u2.getId());
                    if (!pairRepository.checkPair(pair)) {
                        pairRepository.save(pair);
                    }
                }
            }
        }
    }

    public void refresh() {
        userRepository.refresh();
    }
}