package com.senior.project.genealogy.view.activity.login;

import com.senior.project.genealogy.response.User;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginModel mLoginModel;
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.mLoginView = loginView;
        mLoginModel = new LoginModelImpl(loginView);
    }

    @Override
    public void login(final User user) {
        mLoginModel.login(user);
    }
}
