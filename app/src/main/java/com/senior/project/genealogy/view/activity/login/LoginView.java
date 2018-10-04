package com.senior.project.genealogy.view.activity.login;


public interface  LoginView {
    void showToast(String msg);
    void showActivity(Class<?> cls);
    void showProgressDialog();
    void closeProgressDialog();
    void saveToken(String token);
}
