package com.shawn.tongji.cloudaddressbook.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.shawn.tongji.cloudaddressbook.MyApplication;

/**
 * Created by shawn on 4/20/15.
 */
public class MySharedPreferences {

    static MySharedPreferences instance = null;

    SharedPreferences sharedPreferences = null;

    static public String LOGIN_EMAIL = "LOGIN_EMAIL";
    static public String PASSWORD = "PASSWORD";
    public static String USER_ID = "USER_ID";

    MySharedPreferences() {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static MySharedPreferences getInstance() {
        if (instance == null) {
            instance = new MySharedPreferences();
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public int getUserId() {
       return getSharedPreferences().getInt(MySharedPreferences.USER_ID, -1);
    }

    public void setUserId(int userId) {
        SharedPreferences.Editor editor = MySharedPreferences.getInstance().getSharedPreferences().edit();
        editor.putInt(MySharedPreferences.USER_ID, userId);
        editor.commit();
    }
}
