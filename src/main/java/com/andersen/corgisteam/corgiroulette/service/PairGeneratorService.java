package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.entity.User;

public interface PairGeneratorService extends PairService {

    boolean validatePair(User user, User opponent);
}