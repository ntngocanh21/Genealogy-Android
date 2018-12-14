package com.senior.project.genealogy.view.fragment.notification;

public class NotificationPresenterImpl implements NotificationPresenter {

    private NotificationModel mNotificationModel;
    private NotificationView mNotificationView;

    public NotificationPresenterImpl(NotificationView notificationView) {
        mNotificationView = notificationView;
        mNotificationModel = new NotificationModelImpl(this);
    }


    @Override
    public void getNotifications(String token) {
        mNotificationView.showProgressDialog();
        mNotificationModel.getNotifications(token);
    }
}
