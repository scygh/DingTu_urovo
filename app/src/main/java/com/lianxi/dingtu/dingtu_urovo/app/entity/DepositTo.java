package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.util.List;

public class DepositTo {
    private int Count;
    private List<DepositReportTo> Rows;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public List<DepositReportTo> getRows() {
        return Rows;
    }

    public void setRows(List<DepositReportTo> rows) {
        Rows = rows;
    }
}
