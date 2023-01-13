package com.andersen.corgisteam.corgiroulette.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mark {

    private long id;

    private User user;

    private double mark;

    private LocalDateTime dateTime;

    public Mark() {
    }

    public Mark(long id, User user, double mark, LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.mark = mark;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark1 = (Mark) o;
        return id == mark1.id && mark == mark1.mark && user.equals(mark1.user) && dateTime.equals(mark1.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, mark, dateTime);
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", user=" + user +
                ", mark=" + mark +
                ", dateTime=" + dateTime +
                '}';
    }
}
