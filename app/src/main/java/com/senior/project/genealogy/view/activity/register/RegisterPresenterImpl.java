package com.senior.project.genealogy.view.activity.register;

import com.senior.project.genealogy.response.User;

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterModel mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mRegisterModel = new RegisterModelImpl(mRegisterView);
    }

    @Override
    public void register(final User user) {
        mRegisterModel.register(user);
    }
}
