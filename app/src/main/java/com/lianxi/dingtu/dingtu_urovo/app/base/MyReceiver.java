package com.lianxi.dingtu.dingtu_urovo.app.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lianxi.dingtu.dingtu_urovo.mvp.ui.activity.SplashActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent i = new Intent(context, SplashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
