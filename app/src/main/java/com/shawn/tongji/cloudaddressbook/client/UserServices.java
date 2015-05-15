package com.shawn.tongji.cloudaddressbook.client;

import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.bean.UserContactInfo;
import com.shawn.tongji.cloudaddressbook.bean.UserRelation;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserServices {
    @GET("/users/login")
    void login(@Query("email") String email,
               @Query("password") String password,
               Callback<User> callback);

    @POST("/users/user")
    void registration(@Body User user,
                      Callback<User> callback);

    @GET("/users/user")
    void search(@Query("key_word") String keyWord,
                Callback<List<User>> callback);

    @POST("/users/relation/{id}")
    void changeRelation(@Path("id") int idA,
                        @Query("id") int idB,
                        @Query("result") int result,
                        Callback<UserRelation> callback);

    @GET("/users/relation/{id}/{type}")
    void getUserList(@Path("id") int id,
                     @Path("type") int type,
                     Callback<List<User>> callback);

    @GET("/users/relation/{id}")
    void getUserRelation(@Path("id") int idA,
                         @Query("id") int idB,
                         Callback<UserRelation> callback);

    @POST("/users/contacts/{id}")
    public void setContacts(@Path("id") int id,
                            @Body UserContactInfo contacts,
                            Callback<UserContactInfo> callback);
}

