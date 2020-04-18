package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;
import java.util.Date;

public class RegisterParam implements Serializable {

    /**
     * UserID : 00000000-0000-0000-0000-000000000001
     * DepartmentID : 00000000-0000-0000-0000-000000000001
     * DepartmentName : string
     * Name : string
     * EmpID : string
     * IDCard : string
     * Sex : 0
     * Age : 0
     * Address : string
     * Phone : string
     * UserCreateTime : 2018-12-06T03:41:25.360Z
     * Password : string
     * Photo : string
     * PayKey : string
     * CardTypeName : string
     * UserState : 0
     * Number : 0
     * SerialNo : string
     * CardType : 0
     * IsDiscount : true
     * IsGotCard : 0
     * DiscountRate : 0
     * Foregift : 0.01
     * SubsidyLevel : 0
     * SubsidyLevelName : string
     * Cost : 0.01
     * Deadline : 2018-12-06T03:41:25.360Z
     * PayCount : 0
     * LastSubsidyDate : 2018-12-06T03:41:25.360Z
     * SubsidyDatediff : 0
     * CardCreateTime : 2018-12-06T03:41:25.360Z
     * CardState : 0
     * Cash : 0.01
     * Subsidy : 0.01
     * Times : 0
     * Donate : 0.01
     * Integral : 0
     * LastPayDateTime : 2018-12-06T03:41:25.360Z
     * AuthType : 0
     * AuthUrl : string
     * AuthResult : string
     */

    private String DepartmentID;
    private String DepartmentName;
    private String Name;
    private String EmpID;
    private String IDCard;
    private String Address;
    private String Phone;
    private String UserCreateTime;
    private String Password;
    private String PayKey;
    private int Number;
    private String SerialNo;
    private int CardType;
    private int SubsidyLevel;
    private double Cost;
    private String Deadline;
    private Date DeadlineTT;
    private int PayCount;
    private int CardState;
    private double Cash;
    private double Subsidy;
    private double Donate;

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserCreateTime() {
        return UserCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        UserCreateTime = userCreateTime;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPayKey() {
        return PayKey;
    }

    public void setPayKey(String payKey) {
        PayKey = payKey;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public int getCardType() {
        return CardType;
    }

    public void setCardType(int cardType) {
        CardType = cardType;
    }

    public int getSubsidyLevel() {
        return SubsidyLevel;
    }

    public void setSubsidyLevel(int subsidyLevel) {
        SubsidyLevel = subsidyLevel;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }

    public Date getDeadlineTT() {
        return DeadlineTT;
    }

    public void setDeadlineTT(Date deadlineTT) {
        DeadlineTT = deadlineTT;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int payCount) {
        PayCount = payCount;
    }

    public int getCardState() {
        return CardState;
    }

    public void setCardState(int cardState) {
        CardState = cardState;
    }

    public double getCash() {
        return Cash;
    }

    public void setCash(double cash) {
        Cash = cash;
    }

    public double getSubsidy() {
        return Subsidy;
    }

    public void setSubsidy(double subsidy) {
        Subsidy = subsidy;
    }

    public double getDonate() {
        return Donate;
    }

    public void setDonate(double donate) {
        Donate = donate;
    }

}
