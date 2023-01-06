package com.andersen.corgisteam.corgiroulette.pair;

import java.util.Objects;

public class Pair {

    private long idOne;
    private long idTwo;

    public Pair(long idOne, long idTwo) {
        this.idOne = idOne;
        this.idTwo = idTwo;
    }

    public long getIdOne() {
        return idOne;
    }

    public void setIdOne(long idOne) {
        this.idOne = idOne;
    }

    public long getIdTwo() {
        return idTwo;
    }

    public void setIdTwo(long idTwo) {
        this.idTwo = idTwo;
    }


    @Override
    public String toString() {
        return "Pair{" +
                "idOne=" + idOne +
                ", idTwo=" + idTwo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return (idOne == pair.idOne && idTwo == pair.idTwo) ||
                (idOne == pair.idTwo && idTwo == pair.idOne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOne(), getIdTwo()) + Objects.hash(getIdTwo(), getIdOne());
    }
}
