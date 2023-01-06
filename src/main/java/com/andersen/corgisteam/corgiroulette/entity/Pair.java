package com.andersen.corgisteam.corgiroulette.entity;

import java.util.Objects;

public class Pair {

    private long userId;
    private long opponentId;

    public Pair(long userId, long opponentId) {
        this.userId = userId;
        this.opponentId = opponentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(long opponentId) {
        this.opponentId = opponentId;
    }


    @Override
    public String toString() {
        return "Pair{" +
                "userId=" + userId +
                ", opponentId=" + opponentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return (userId == pair.userId && opponentId == pair.opponentId) ||
                (userId == pair.opponentId && opponentId == pair.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getOpponentId()) + Objects.hash(getOpponentId(), getUserId());
    }
}