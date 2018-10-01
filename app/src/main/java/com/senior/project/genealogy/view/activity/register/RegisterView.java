package com.senior.project.genealogy.view.activity.register;

import android.app.ProgressDialog;

public interface RegisterView {
    void showToast(String msg);
    void showActivity(Class<?> cls);
    ProgressDialog showProgressDialog();
    void closeProgressDialog(ProgressDialog progressDialog);
}
