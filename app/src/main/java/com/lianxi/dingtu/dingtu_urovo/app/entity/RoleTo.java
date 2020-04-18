package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.net.IDN;

public class RoleTo {
    /**
     * ID	string($guid)
     * Name	string
     * Modules	string
     * State	integer
     * Code	integer
     */

    private String ID;
    private String Name;
    private String Modules;
    private Integer State;
    private Integer Code;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getModules() {
        return Modules;
    }

    public void setModules(String modules) {
        Modules = modules;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }
}
