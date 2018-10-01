package com.senior.project.genealogy.view.activity.login;

import android.app.ProgressDialog;

import com.senior.project.genealogy.response.User;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginModel mLoginModel;
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
        mLoginModel = new LoginModelImpl(this);
    }

    @Override
    public void login(final User user) {
        mLoginView.showProgressDialog();
        mLoginModel.login(user);
    }



    @Override
    public void loginSuccess() {
        // Close Progressbar
        mLoginView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mLoginView.closeProgressDialog();
        // show option try again
    }
}
