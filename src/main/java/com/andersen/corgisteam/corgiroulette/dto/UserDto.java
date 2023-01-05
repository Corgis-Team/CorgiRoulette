package com.andersen.corgisteam.corgiroulette.dto;

public class UserDto {

    private long id;
    private String name;
    private String surname;
    private long teamId;

    public UserDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getTeamId() {
        return teamId;
    }
}
