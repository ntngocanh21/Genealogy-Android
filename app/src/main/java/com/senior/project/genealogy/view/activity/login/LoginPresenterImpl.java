package com.senior.project.genealogy.view.activity.login;

import com.google.firebase.messaging.FirebaseMessaging;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

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
        mLoginView.saveAccount("", "");
        mLoginView.closeProgressDialog();
        mLoginView.showLoginAgainDialog();
    }

    @Override
    public void loginSuccess(String msg) {
        mLoginView.closeProgressDialog();
        showToast(msg);
        mLoginView.showActivity(HomeActivity.class);
    }

    @Override
    public void showToast(String s) {
        mLoginView.closeProgressDialog();
        mLoginView.showToast(s);
    }

    @Override
    public void saveUser(String token, String avatar, String fullname, String deviceId, String branchId) {
        FirebaseMessaging.getInstance().subscribeToTopic(branchId+ Constants.BRANCH);
        mLoginView.saveUser(token, avatar, fullname, Utils.getDeviceId(), branchId+Constants.BRANCH);
    }
}
