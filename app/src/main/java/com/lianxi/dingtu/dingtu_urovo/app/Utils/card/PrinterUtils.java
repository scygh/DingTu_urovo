package com.lianxi.dingtu.dingtu_urovo.app.Utils.card;

import android.app.Activity;
import android.device.PrinterManager;

import com.lianxi.dingtu.dingtu_urovo.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_urovo.app.api.AppConstant;

public class PrinterUtils {
    private Activity activity;
    private PrinterManager printerManager;

    private static volatile PrinterUtils instance;
    private PrinterUtils(Activity activity) {
        super();
        this.activity = activity;
        init();
    }

    private void init() {
        printerManager = new PrinterManager();
    }

    public static PrinterUtils getInstance(Activity activity) {
        if (instance == null) {
            synchronized (PrinterUtils.class) {
                if (instance == null) {
                    instance = new PrinterUtils(activity);
                }
            }
        }
        return instance;
    }

    public void printLianxi(StringBuilder printer) {
        String Spname = (String) SpUtils.get(activity, AppConstant.Receipt.NAME,"杭州鼎图信息");
        String Spaddress = (String) SpUtils.get(activity, AppConstant.Receipt.ADDRESS,"");
        String Spphone = (String) SpUtils.get(activity, AppConstant.Receipt.PHONE,"");

        printerManager.clearPage();
        printerManager.setupPage(-1,500);
        printerManager.drawText(Spname,38,0,"arial",50,false,false,0);
        printerManager.drawText("",0,50,"simsun",24,false,false,0);
        printerManager.drawText("------------------------------",0,55,"simsun",24,true,false,0);
        printerManager.drawText(printer.toString(),0,60,"simsun",24,false,false,0);
        printerManager.drawText("------------------------------",0,245,"simsun",24,true,false,0);
        printerManager.drawText("地址："+Spaddress,0,270,"simsun",24,false,false,0);
        printerManager.drawText("电话："+Spphone,0,300,"simsun",24,false,false,0);

        printerManager.printPage(0);
    }

    public void close() {
        printerManager.close();
    }
}
