package com.senior.project.genealogy.view.activity.login;

import android.util.Log;
import com.senior.project.genealogy.response.LoginResponse;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginModelImpl implements LoginModel {
    private LoginView mLoginView;
    private ApplicationApi mApplicationApi;

    public LoginModelImpl(LoginView loginView) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        this.mLoginView = loginView;
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
                        mLoginView.showToast(String.valueOf(loginResponse.getError().getDescription()));
                        String token = String.valueOf(loginResponse.getToken());
                        Log.d("TAG", token);
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        mLoginView.showToast(String.valueOf(loginResponse.getError().getDescription()));
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
