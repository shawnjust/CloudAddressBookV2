package com.shawn.tongji.cloudaddressbook;

import android.app.Application;
import android.content.Context;

/**
 * Created by shawn on 4/16/15.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}
