package com.shawn.tongji.cloudaddressbook.net;

import android.widget.Toast;

import com.shawn.tongji.cloudaddressbook.MyApplication;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by shawn on 5/13/15.
 */
public abstract class MyCallBack<T extends Object> implements Callback<T> {

    @Override
    public void failure(RetrofitError error) {
        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {


            Toast.makeText(MyApplication.getAppContext(), "网络错误，请检查网络连接", Toast.LENGTH_SHORT).show();
            return;

        } else if (error.getKind().equals(RetrofitError.Kind.HTTP)) {

            int statusCode = error.getResponse().getStatus();
            if (!httpFailure(statusCode, error)) {
                switch (statusCode) {
                    case UrlUtil.SERVER_ERROR:
                        Toast.makeText(MyApplication.getAppContext(), "服务器错误 " + statusCode, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MyApplication.getAppContext(), "请求错误 " + statusCode, Toast.LENGTH_SHORT).show();
                        break;
                }

            }

        } else {
            throw error;
        }
    }

    public boolean httpFailure(int statusCode, RetrofitError error) {
        return false;
    }
}
