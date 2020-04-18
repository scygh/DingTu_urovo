package com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.lianxi.dingtu.dingtu_urovo.BuildConfig;

public class UpdateConfig {



    private static final String TAG = "Update";
    public static final String APK_PACKAGENAME ="com.lianxi.dingtu.newsnfc";

    public static int getVerCode() {
        int verCode = BuildConfig.VERSION_CODE;
        return verCode;
    }

    public static String getVerName() {
        String VerName = BuildConfig.VERSION_NAME;
        return VerName;

    }

}
