package com.andersen.corgisteam.corgiroulette.entity;


import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private long id;
    private String name;
    private String surname;
    private long teamId;
    private boolean isChosen;
    private LocalDateTime lastDuel;

    public User() {
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

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
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
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getTeamId() == user.getTeamId() &&
                isChosen() == user.isChosen() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getLastDuel(), user.getLastDuel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getTeamId(), isChosen(), getLastDuel());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", groupId=" + teamId +
                ", isChosen=" + isChosen +
                ", lastCompetition=" + lastDuel +
                '}';
    }
}