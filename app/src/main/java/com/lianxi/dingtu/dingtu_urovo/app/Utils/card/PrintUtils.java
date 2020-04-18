package com.lianxi.dingtu.dingtu_urovo.app.Utils.card;

import android.device.PrinterManager;

public class PrintUtils {

    private PrinterManager printerManager = new PrinterManager();

    public void init(){
        printerManager.open();
    }
}
