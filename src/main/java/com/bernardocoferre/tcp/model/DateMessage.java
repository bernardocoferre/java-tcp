package com.bernardocoferre.tcp.model;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "date_messages")
public class DateMessage {

    @Id
    @Column
    @SequenceGenerator(name = "sq_date_messages", sequenceName = "sq_date_messages")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_date_messages")
    protected Integer id;

    @Column
    protected int day;

    @Column
    protected int month;

    @Column
    protected int year;

    @Column
    protected int hour;

    @Column
    protected int minute;

    @Column
    protected int second;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

}
