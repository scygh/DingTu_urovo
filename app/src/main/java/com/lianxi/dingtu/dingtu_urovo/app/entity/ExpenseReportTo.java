package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;

public class ExpenseReportTo implements Serializable {


    /**
     * ID : 5b86266b-ffe6-4741-80ff-0233fae4ebd9
     * UserID : 01d23dab-c4ad-4112-84bb-9293f3c74e2d
     * SerialNo : p5
     * Name : 鲁炳
     * DepartmentID : 00000000-0000-0000-0000-000000000000
     * DepartmentName :
     * EmpID : AA
     * IDCard : AA
     * Phone : 17557289603
     * Number : 20
     * CardTypeID : 1
     * CardTypeName : 正常卡
     * DeviceType : 1
     * DeviceTypeName : 洗衣机
     * DeviceID : 2
     * DetailType : 3
     * PayCount : 2
     * Pattern : 2
     * PatternName : 自动扣费
     * Finance : 0
     * OriginalAmount : 4.01
     * Amount : 4.01
     * Balance : 492.01
     * IsDiscount : false
     * DiscountRate : 100
     * TradeDateTime : 2018-10-09 15:27:25
     * Description : 【洗衣机使用】,用户：鲁炳使用单脱程序;花费4.00元
     * OfflinePayCount : 0
     */

    private String ID;
    private String UserID;
    private String SerialNo;
    private String Name;
    private String DepartmentID;
    private String DepartmentName;
    private String EmpID;
    private String IDCard;
    private String Phone;
    private int Number;
    private int CardTypeID;
    private String CardTypeName;
    private int DeviceType;
    private String DeviceTypeName;
    private int DeviceID;
    private int DetailType;
    private int PayCount;
    private int Pattern;
    private String PatternName;
    private int Finance;
    private double OriginalAmount;
    private double Amount;
    private double Balance;
    private boolean IsDiscount;
    private int DiscountRate;
    private String TradeDateTime;
    private String Description;
    private int OfflinePayCount;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

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

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
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

    public int getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(int DeviceType) {
        this.DeviceType = DeviceType;
    }

    public String getDeviceTypeName() {
        return DeviceTypeName;
    }

    public void setDeviceTypeName(String DeviceTypeName) {
        this.DeviceTypeName = DeviceTypeName;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
    }

    public int getDetailType() {
        return DetailType;
    }

    public void setDetailType(int DetailType) {
        this.DetailType = DetailType;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int PayCount) {
        this.PayCount = PayCount;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int Pattern) {
        this.Pattern = Pattern;
    }

    public String getPatternName() {
        return PatternName;
    }

    public void setPatternName(String PatternName) {
        this.PatternName = PatternName;
    }

    public int getFinance() {
        return Finance;
    }

    public void setFinance(int Finance) {
        this.Finance = Finance;
    }

    public double getOriginalAmount() {
        return OriginalAmount;
    }

    public void setOriginalAmount(double OriginalAmount) {
        this.OriginalAmount = OriginalAmount;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public boolean isIsDiscount() {
        return IsDiscount;
    }

    public void setIsDiscount(boolean IsDiscount) {
        this.IsDiscount = IsDiscount;
    }

    public int getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(int DiscountRate) {
        this.DiscountRate = DiscountRate;
    }

    public String getTradeDateTime() {
        return TradeDateTime;
    }

    public void setTradeDateTime(String TradeDateTime) {
        this.TradeDateTime = TradeDateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getOfflinePayCount() {
        return OfflinePayCount;
    }

    public void setOfflinePayCount(int OfflinePayCount) {
        this.OfflinePayCount = OfflinePayCount;
    }
}
