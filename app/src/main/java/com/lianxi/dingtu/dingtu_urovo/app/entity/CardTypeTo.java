package com.lianxi.dingtu.dingtu_urovo.app.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class CardTypeTo implements Serializable, IPickerViewData {

    /**
     * ID : 1
     * Name : 正常卡
     * State : 1
     * Foregift : 100
     * DefaultCost : 10.01
     * ExpiryDate : 3600
     * IsDiscount : true
     * DiscountRate : 10
     * Description : 正常卡
     */

    private int Id;
    private String Name;
    private int State;
    private int Foregift;
    private double DefaultCost;
    private int ExpiryDate;
    private boolean IsDiscount;
    private int DiscountRate;
    private String Description;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getForegift() {
        return Foregift;
    }

    public void setForegift(int Foregift) {
        this.Foregift = Foregift;
    }

    public double getDefaultCost() {
        return DefaultCost;
    }

    public void setDefaultCost(double DefaultCost) {
        this.DefaultCost = DefaultCost;
    }

    public int getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(int ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override public String getPickerViewText() {
        return Name;
    }
}
