package com.senior.project.genealogy.view.fragment.notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragment extends Fragment implements NotificationView {

    @BindView(R.id.rcvNotifications)
    RecyclerView rcvNotifications;

    private String token;
    private ProgressDialog mProgressDialog;

    private NotificationPresenter mNotificationPresenter;

    public NotificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_branches));
        mNotificationPresenter = new NotificationPresenterImpl(this);
        mNotificationPresenter.getNotifications(token);
        return view;
    }

    public ProgressDialog initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

}
