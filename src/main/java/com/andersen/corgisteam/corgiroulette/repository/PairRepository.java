package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.entity.Pair;

import java.util.List;

public interface PairRepository {

    void save(Pair pair);

    List<Pair> findAll();

    boolean checkPair(Pair pair);

    void createPairInBattleTable(long userId, long opponentId);

    void deleteUserOpponent(long userId);
}
