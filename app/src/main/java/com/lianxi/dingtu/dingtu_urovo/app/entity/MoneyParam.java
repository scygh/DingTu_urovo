package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;

public class MoneyParam implements Serializable {

    /**
     * ID : 00000000-0000-0000-0000-000000000000
     * UserID : 00000000-0000-0000-0000-000000000000
     * Number : 0
     * DeviceID : 0
     * DetailType : 1
     * Finance : 0
     * PayCount : 0
     * BeforeBalance : 0.01
     * Amount : 0.01chong
     * Money : 0.01shou
     * Donate : 0.01song
     * Cost : 0.01
     * AfterBalance : 0.01
     * Operator : string
     * TradeDateTime : 2018-12-06T06:07:28.875Z
     * CreateTime : 2018-12-06T06:07:28.875Z
     * Description : string
     * Channel : 0
     */


    private String UserID;
    private int Number;
    private int DeviceID;
    private double Amount;
    private double Money;
    private double Donate;
    private double Cost;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int deviceID) {
        DeviceID = deviceID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double money) {
        Money = money;
    }

    public double getDonate() {
        return Donate;
    }

    public void setDonate(double donate) {
        Donate = donate;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }
}
