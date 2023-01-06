package com.andersen.corgisteam.corgiroulette.pair;

import java.util.List;

public interface PairRepository {

    void save(Pair pair);

    List<Pair> findAll();

    boolean checkPair(Pair pair);
}
