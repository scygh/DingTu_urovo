package com.lianxi.dingtu.dingtu_urovo.app.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class DepartmentTo implements Serializable, IPickerViewData {

    /**
     * ID : 00000000-0000-0000-0000-000000000001
     * ParentID : “”
     * Name : 默认部门
     * Description : 系统默认的部门
     * State : 1
     */

    private String Id;
    private String ParentId;
    private String Name;
    private String Description;
    private int State;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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
