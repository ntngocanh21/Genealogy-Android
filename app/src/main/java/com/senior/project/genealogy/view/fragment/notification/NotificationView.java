package com.senior.project.genealogy.view.fragment.notification;

import com.senior.project.genealogy.response.Notification;

import java.util.List;

public interface NotificationView {
    void showProgressDialog();

    void closeProgressDialog();

    void getListNotifications(List<Notification> notificationList);
}
