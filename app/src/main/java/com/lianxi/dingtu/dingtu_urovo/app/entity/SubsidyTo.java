package com.lianxi.dingtu.dingtu_urovo.app.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class SubsidyTo implements Serializable, IPickerViewData {

    /**
     * ID : 00000000-0000-0000-0000-000000000001
     * Leve : 0
     * Name : 无补贴
     * Amount : 0.01
     * IsReset : false
     * State : 1
     * Description : 系统默认无补贴
     */

    private String Id;
    private int Leve;
    private String Name;
    private double Amount;
    private boolean IsReset;
    private int State;
    private String Description;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getLeve() {
        return Leve;
    }

    public void setLeve(int Leve) {
        this.Leve = Leve;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public boolean isIsReset() {
        return IsReset;
    }

    public void setIsReset(boolean IsReset) {
        this.IsReset = IsReset;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
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
