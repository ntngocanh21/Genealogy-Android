package com.senior.project.genealogy.view.activity.login;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.view.activity.search.SearchActivity;

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
    public void loginFalse() {
        mLoginView.closeProgressDialog();
        mLoginView.showToast("False");
        // show option try again
    }

    @Override
    public void loginSuccess(String msg) {
        mLoginView.closeProgressDialog();
        showToast(msg);
        mLoginView.showActivity(SearchActivity.class);
    }

    @Override
    public void showToast(String s) {
        mLoginView.closeProgressDialog();
        mLoginView.showToast(s);
    }

    @Override
    public void saveToken(String token) {
        mLoginView.saveToken(token);
    }
}
