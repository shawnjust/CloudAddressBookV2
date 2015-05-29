package com.shawn.tongji.cloudaddressbook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.shawn.tongji.cloudaddressbook.MyApplication;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.net.UrlUtil;
import com.shawn.tongji.cloudaddressbook.net.VolleySingleton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shawn on 4/20/15.
 */
public class MySharedPreferences {


    static MySharedPreferences instance = null;

    SharedPreferences sharedPreferences = null;

    static public String LOGIN_EMAIL = "LOGIN_EMAIL";
    static public String PASSWORD = "PASSWORD";
    private static final String USER_ID = "USER_ID";
    private static final String USER = "USER";

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

//    public int getUserId() {
//        return getSharedPreferences().getInt(MySharedPreferences.USER_ID, -1);
//    }

    public User getUser() {
        String userStr =  getSharedPreferences().getString(MySharedPreferences.USER, "");
        if ("".equals(userStr)) {
            return null;
        } else {
            User user = new Gson().fromJson(userStr, User.class);
            return user;
        }
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = MySharedPreferences.getInstance().getSharedPreferences().edit();
        editor.putString(MySharedPreferences.USER, new Gson().toJson(user));
        editor.commit();
    }

    public void clearUser() {
        SharedPreferences.Editor editor = MySharedPreferences.getInstance().getSharedPreferences().edit();
        editor.remove(MySharedPreferences.USER);
        editor.commit();
    }

    public interface OnBitMapGetCallback {
        void onGetBitmap(Bitmap bitmap);
    }

    public void getUserHeader(final User user, boolean immediate, @NonNull final OnBitMapGetCallback onBitMapGetCallback) {
        if (immediate) {
            getUserHeaderFromLink(user, onBitMapGetCallback);
        } else {
            Bitmap bitmap = getUserHeaderFromFile(user);
            if (bitmap != null) {
                onBitMapGetCallback.onGetBitmap(bitmap);
            } else {
                getUserHeaderFromLink(user, onBitMapGetCallback);
            }
        }
    }

    public void getUserHeaderFromLink(final User user, @NonNull final OnBitMapGetCallback onBitMapGetCallback) {
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
        imageLoader.get(UrlUtil.getImageView(MySharedPreferences.getInstance().getUser().getUserId(), user.getUserHeader()), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null && onBitMapGetCallback != null) {
                    onBitMapGetCallback.onGetBitmap(response.getBitmap());
                    try {
                        saveUserHeaderToFile(user, response.getBitmap());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }, 1024, 1024);
    }

    public Bitmap getUserHeaderFromFile(User user) {
        try {
            FileInputStream in = MyApplication.getInstance().openFileInput("header_" + user.getUserId() + ".png");
            Bitmap result = BitmapFactory.decodeStream(in);
            Log.e(this.getClass().getName(), "Success read bitmap where id = " + user.getUserId());
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void saveUserHeaderToFile(User user, Bitmap bitmap) throws IOException {
        FileOutputStream out = MyApplication.getInstance().openFileOutput("header_" + user.getUserId() + ".png", Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        out.flush();
        out.close();
    }
}
