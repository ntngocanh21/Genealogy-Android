package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.response.UserResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.MemberApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailMemberRequestBranchModelImpl implements DetailMemberRequestBranchModel {
    private DetailMemberRequestBranchFragmentPresenter mDetailMemberRequestBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailMemberRequestBranchModelImpl(DetailMemberRequestBranchFragmentPresenter detailMemberRequestBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailMemberRequestBranchFragmentPresenter = detailMemberRequestBranchFragmentPresenter;
    }

    @Override
    public void getRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token) {
        Call<UserResponse> call = mApplicationApi.getClient().create(MemberApi.class).getMemberOfBranch(userBranchPermission, token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                int code = Integer.parseInt(userResponse.getError().getCode());
                if (code == Constants.HTTPCodeResponse.SUCCESS) {
                    mDetailMemberRequestBranchFragmentPresenter.getRequestMemberOfBranchSuccess(userResponse.getUserList());
                } else {
                    mDetailMemberRequestBranchFragmentPresenter.getRequestMemberOfBranchFalse();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mDetailMemberRequestBranchFragmentPresenter.getRequestMemberOfBranchFalse();
            }
        });
    }
}
