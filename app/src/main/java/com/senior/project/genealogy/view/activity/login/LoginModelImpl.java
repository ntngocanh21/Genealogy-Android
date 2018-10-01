package com.senior.project.genealogy.view.activity.login;

import android.app.ProgressDialog;
import android.util.Log;
import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.search.SearchActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModelImpl implements LoginModel {
    private LoginPresenter mLoginPresenter;
    private ApplicationApi mApplicationApi;

    public LoginModelImpl(LoginPresenter loginPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mLoginPresenter = loginPresenter;
    }

    @Override
    public void login(final User user) {
        Call<LoginResponse> call = mApplicationApi.getClient().create(UserApi.class).login(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                int code = Integer.parseInt(loginResponse.getError().getCode());
                switch (code){
                    case Constants.HTTPCodeResponse.SUCCESS:
//                        mLoginView.showToast(String.valueOf(loginResponse.getError().getDescription()));
//                        String token = String.valueOf(loginResponse.getToken());
//                        Log.d("TAG", token);
//                        mLoginView.closeProgressDialog(progressDialog);
//                        mLoginView.showActivity(SearchActivity.class);
                        mLoginPresenter.loginSuccess();
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mLoginPresenter.showToast(String.valueOf(loginResponse.getError().getDescription()));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
