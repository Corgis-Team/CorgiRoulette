package com.andersen.corgisteam.corgiroulette.dto;

import com.andersen.corgisteam.corgiroulette.entity.User;

public class ResponseGroupedMarksDto {
    private User user;

    private double mark;

    public ResponseGroupedMarksDto() {
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }
}
