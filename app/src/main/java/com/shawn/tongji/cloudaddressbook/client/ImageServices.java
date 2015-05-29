package com.shawn.tongji.cloudaddressbook.client;

import com.shawn.tongji.cloudaddressbook.bean.User;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

public interface ImageServices {

    @Multipart
    @POST("/image/{id}")
    void uploadImage(@Path("id") int id,
                     @Part("file") TypedFile bitmap,
                     Callback<User> callback);
}
