package com.andersen.corgisteam.corgiroulette.service.impl;

import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;
import com.andersen.corgisteam.corgiroulette.repository.PairRepository;
import com.andersen.corgisteam.corgiroulette.repository.UserRepository;

import java.util.List;

public class PairGenerator {

    private final UserRepository userRepository;
    private final PairRepository pairRepository;

    public PairGenerator(UserRepository userRepository, PairRepository pairRepository) {
        this.pairRepository = pairRepository;
        this.userRepository = userRepository;
    }

    public void generatePairs() {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            for (int j = users.size() - 1; j > 0; j--) {
                User u1 = users.get(i);
                User u2 = users.get(j);
                if (u1.getTeam().getId() != u2.getTeam().getId()) {
                    Pair pair = new Pair(u1, u2);
                    if (!pairRepository.checkPair(pair)) {
                        pairRepository.save(pair);
                    }
                }
            }
        }
    }
}
