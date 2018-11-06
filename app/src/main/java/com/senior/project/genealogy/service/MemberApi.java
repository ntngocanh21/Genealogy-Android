package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MemberApi {

    @POST("api/member")
    Call<UserResponse> getMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @DELETE("api/member")
    Call<UserResponse> declineRequestMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @PUT("api/member")
    Call<UserResponse> acceptRequestMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

    @PUT("api/member/role")
    Call<UserResponse> changeRoleMemberOfBranch(@Body UserBranchPermission userBranchPermission, @Header("Authorisation") String token);

}
