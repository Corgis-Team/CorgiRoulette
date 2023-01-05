package com.andersen.corgisteam.corgiroulette.dto;

public class UserDto {
    private String name;
    private String surname;
    private long teamId;

    public UserDto() {
    }

    public UserDto(String name, String surname, long teamId) {
        this.name = name;
        this.surname = surname;
        this.teamId = teamId;
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
