package com.senior.project.genealogy.view.activity.register;

import android.util.Log;

import com.senior.project.genealogy.response.Message;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModelImpl implements RegisterModel {
    private RegisterView mRegisterView;
    private ApplicationApi mApplicationApi;

    public RegisterModelImpl(RegisterView loginView) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        this.mRegisterView = loginView;
    }

    @Override
    public void register(final User user) {
        Call<Message> call = mApplicationApi.getClient().create(UserApi.class).register(user);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                int code = Integer.parseInt(message.getCode());
                switch (code){
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mRegisterView.showToast(String.valueOf(message.getDescription()));
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_EXISTED:
                        mRegisterView.showToast(String.valueOf(message.getDescription()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
