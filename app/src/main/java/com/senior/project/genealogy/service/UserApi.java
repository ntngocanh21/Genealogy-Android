package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Call<LoginResponse> login(@Body User user);

    @POST("register")
    Call<LoginResponse> register(@Body User user);

}
