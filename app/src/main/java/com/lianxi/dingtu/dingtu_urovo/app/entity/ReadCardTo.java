package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;

public class ReadCardTo implements Serializable {

    /**
     * State : 0
     * Number : 102
     * Balance : 750.0
     * PayCount : 55
     * UserName : 哆啦A梦
     */

    private int State;
    private int Number;
    private double Balance;
    private int PayCount;
    private String UserName;

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int PayCount) {
        this.PayCount = PayCount;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
