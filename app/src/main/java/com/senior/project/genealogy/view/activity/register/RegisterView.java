package com.senior.project.genealogy.view.activity.register;

public interface RegisterView {
    void showToast(String msg);
    void showActivity(Class<?> cls);
    void showProgressDialog();
    void closeProgressDialog();
    void saveToken(String token);
    void saveAccount(String username, String password);
}
