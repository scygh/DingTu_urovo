package com.lianxi.dingtu.dingtu_urovo.app.entity;

public class MachineAmountTo {
    /**
     * "LastDay": 0,
     *     "Today": 0.12,
     *     "CurrentMonth": 3396.34,
     *     "lastMonth": 10478.54
     */
    private double LastDay;
    private double Today;
    private double CurrentMonth;
    private double LastMonth;
    private int ToDayCount;

    public int getToDayCount() {
        return ToDayCount;
    }

    public void setToDayCount(int toDayCount) {
        ToDayCount = toDayCount;
    }

    public double getLastDay() {
        return LastDay;
    }

    public void setLastDay(double lastDay) {
        LastDay = lastDay;
    }

    public double getToday() {
        return Today;
    }

    public void setToday(double today) {
        Today = today;
    }

    public double getCurrentMonth() {
        return CurrentMonth;
    }

    public void setCurrentMonth(double currentMonth) {
        CurrentMonth = currentMonth;
    }

    public double getLastMonth() {
        return LastMonth;
    }

    public void setLastMonth(double lastMonth) {
        this.LastMonth = lastMonth;
    }
}
