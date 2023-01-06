package com.andersen.corgisteam.corgiroulette.entity;

import java.util.Objects;

public class Pair {

    private User user;
    private User opponent;

    public Pair(User user, User opponent) {
        this.user = user;
        this.opponent = opponent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpponent() {
        return opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return (Objects.equals(user, pair.user) && Objects.equals(opponent, pair.opponent)) ||
                (Objects.equals(opponent, pair.user) && Objects.equals(user, pair.opponent));
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, opponent) + Objects.hash(opponent, user);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "user=" + user +
                ", opponent=" + opponent +
                '}';
    }
}