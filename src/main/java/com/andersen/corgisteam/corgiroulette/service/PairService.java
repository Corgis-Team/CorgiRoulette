package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.entity.Pair;
import com.andersen.corgisteam.corgiroulette.entity.User;

public interface PairService {

    Pair getPair();

    Pair changeOpponent(User userToSave, User userToChange);
}
