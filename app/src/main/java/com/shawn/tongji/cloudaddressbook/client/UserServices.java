package com.shawn.tongji.cloudaddressbook.client;

import com.shawn.tongji.cloudaddressbook.bean.User;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserServices {
    @GET("/users/login")
    void login(@Query("email") String email, @Query("password") String password, Callback<User> callback);

    @POST("/users/user")
    void registration(@Body User user, Callback<User> callback);

    @GET("/users/user")
    void search(@Query("key_word") String keyWord, Callback<List<User>> callback);

    @GET("/users/contacts/{id}")
    void getUserContacts(@Path("id") int id, Callback<Response> callback);

    @POST("/users/relation/{id}")
    void addFriend(@Path("id") int fromId, @Query("id") int toId, Callback<Response> callback);

    @PUT("/users/relation/{id}")
    void acceptFriend(@Path("id") int toId, @Query("id") int fromId, @Query("accept") int result, Callback<Response> callback);

    @GET("/users/relation/{id}")
    void getRelationList(@Path("id") int id, @Query("type") int type, Callback<Response> callback);
}

