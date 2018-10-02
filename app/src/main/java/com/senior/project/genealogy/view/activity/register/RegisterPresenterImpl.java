package com.senior.project.genealogy.view.activity.register;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.view.activity.search.SearchActivity;

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterModel mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mRegisterModel = new RegisterModelImpl(this);
    }

    @Override
    public void registerSuccess(String msg) {
        mRegisterView.closeProgressDialog();
        showToast(msg);
        mRegisterView.showActivity(SearchActivity.class);
    }

    @Override
    public void registerFalse() {
        mRegisterView.closeProgressDialog();
        // show option try again
    }

    @Override
    public void showToast(String s) {
        mRegisterView.closeProgressDialog();
        mRegisterView.showToast(s);
    }

    @Override
    public void register(final User user) {
        mRegisterView.showProgressDialog();
        mRegisterModel.register(user);
    }

}
