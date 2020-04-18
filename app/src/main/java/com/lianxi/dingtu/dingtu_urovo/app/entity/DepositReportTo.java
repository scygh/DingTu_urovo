package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;

public class DepositReportTo implements Serializable {
    /**
     * SerialNo : 000001
     * Name : 鲁炳
     * DepartmentID : 00000000-0000-0000-0000-000000000001
     * EmpID :
     * IDCard : 4245646548648
     * Phone : 17557289603
     * Number : 100
     * CardTypeID : 1
     * CardTypeName : 正常卡
     * DetailType : 1
     * DeviceID : 1
     * Finance : 0
     * BeforeBalance : 36.01
     * Amount : 9.01
     * Money : 9.01
     * Donate : 0.01
     * Cost : 0.01
     * UserID : e1809350-d361-4725-98c6-46b9800796b0
     * AfterBalance : 45.01
     * TradeDateTime : 2018-11-08 11:42:28
     * Operator : 机器
     * Description : 机器充值【线上】充值现金9元,充值赠送0元,实际现金交易9元,余额45.00元
     * Channel :
     */

    private String SerialNo;
    private String Name;
    private String DepartmentID;
    private String EmpID;
    private String IDCard;
    private String Phone;
    private int Number;
    private int CardTypeID;
    private String CardTypeName;
    private int DetailType;
    private int DeviceID;
    private int Finance;
    private double BeforeBalance;
    private double Amount;
    private double Money;
    private double Donate;
    private double Cost;
    private String UserID;
    private double AfterBalance;
    private String TradeDateTime;
    private String Operator;
    private String Description;
    private String Channel;

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String EmpID) {
        this.EmpID = EmpID;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getCardTypeID() {
        return CardTypeID;
    }

    public void setCardTypeID(int CardTypeID) {
        this.CardTypeID = CardTypeID;
    }

    public String getCardTypeName() {
        return CardTypeName;
    }

    public void setCardTypeName(String CardTypeName) {
        this.CardTypeName = CardTypeName;
    }

    public int getDetailType() {
        return DetailType;
    }

    public void setDetailType(int DetailType) {
        this.DetailType = DetailType;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
    }

    public int getFinance() {
        return Finance;
    }

    public void setFinance(int Finance) {
        this.Finance = Finance;
    }

    public double getBeforeBalance() {
        return BeforeBalance;
    }

    public void setBeforeBalance(double BeforeBalance) {
        this.BeforeBalance = BeforeBalance;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double Money) {
        this.Money = Money;
    }

    public double getDonate() {
        return Donate;
    }

    public void setDonate(double Donate) {
        this.Donate = Donate;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public double getAfterBalance() {
        return AfterBalance;
    }

    public void setAfterBalance(double AfterBalance) {
        this.AfterBalance = AfterBalance;
    }

    public String getTradeDateTime() {
        return TradeDateTime;
    }

    public void setTradeDateTime(String TradeDateTime) {
        this.TradeDateTime = TradeDateTime;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String Operator) {
        this.Operator = Operator;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String Channel) {
        this.Channel = Channel;
    }

}
