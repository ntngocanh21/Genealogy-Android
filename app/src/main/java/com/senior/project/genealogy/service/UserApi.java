package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.Message;
import com.senior.project.genealogy.response.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Call<LoginResponse> login(@Body User user);

    @POST("register")
    Call<Message> register(@Body User user);

}
