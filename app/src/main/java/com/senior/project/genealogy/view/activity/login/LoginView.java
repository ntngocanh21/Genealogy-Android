package com.senior.project.genealogy.view.activity.login;

import android.app.ProgressDialog;

public interface  LoginView {
    void showToast(String msg);
    void showActivity(Class<?> cls);
    ProgressDialog showProgressDialog();
    void closeProgressDialog(ProgressDialog progressDialog);
}
