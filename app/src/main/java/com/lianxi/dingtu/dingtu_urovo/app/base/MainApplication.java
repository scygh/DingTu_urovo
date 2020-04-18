package com.lianxi.dingtu.dingtu_urovo.app.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.Preconditions;


public class MainApplication extends MultiDexApplication implements App {

    private AppLifecycles mAppDelegate;
    public static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        if (mAppDelegate==null){
            this.mAppDelegate=new AppDelegate(base);
        }
        this.mAppDelegate.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this.getApplicationContext();
        if (mAppDelegate != null){
            this.mAppDelegate.onCreate(this);
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate!=null){
            this.mAppDelegate.onTerminate(this);
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        com.jess.arms.utils.Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }


}
