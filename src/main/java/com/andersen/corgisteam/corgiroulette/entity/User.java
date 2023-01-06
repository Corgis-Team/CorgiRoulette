package com.andersen.corgisteam.corgiroulette.entity;


import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private long id;
    private String name;
    private String surname;
    private Team team;
    private boolean isChosen;
    private LocalDateTime lastDuel;

    public User() {
    }

    public User(long id, String name, String surname, Team team, boolean isChosen, LocalDateTime lastDuel) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.team = team;
        this.isChosen = isChosen;
        this.lastDuel = lastDuel;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public LocalDateTime getLastDuel() {
        return lastDuel;
    }

    public void setLastDuel(LocalDateTime lastDuel) {
        this.lastDuel = lastDuel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return isChosen() == user.isChosen() &&
            Objects.equals(getName(), user.getName()) &&
            Objects.equals(getSurname(), user.getSurname()) &&
            Objects.equals(getTeam(), user.getTeam()) &&
            Objects.equals(getLastDuel(), user.getLastDuel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getTeam(), isChosen(), getLastDuel());
    }
}