package com.lianxi.dingtu.dingtu_urovo.app.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class EMGoodsTypeTo implements Serializable, IPickerViewData {

    /**
     * ID : e0aeae8c-b241-4de1-9f1b-6f2fffcf6ed2
     * Name : 肉类
     * ParentID :
     * Description :
     * State : 1
     */

    private String Id;
    private String Name;
    private String ParentId;
    private String Description;
    private int State;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    @Override public String getPickerViewText() {
        return Name;
    }
}
