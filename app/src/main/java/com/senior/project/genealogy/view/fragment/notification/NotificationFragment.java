package com.senior.project.genealogy.view.fragment.notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.app.GenealogyApplication;
import com.senior.project.genealogy.response.Notification;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.branch.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationFragment extends Fragment implements NotificationView {

    @BindView(R.id.rcvNotifications)
    RecyclerView rcvNotifications;

    private String token;
    private ProgressDialog mProgressDialog;

    private NotificationPresenter mNotificationPresenter;

    private List<Notification> mListNotifications;
    private NotificationAdapter mNotificationAdapter;

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

        initAttributes();
        return view;
    }

    protected void initAttributes() {
        mNotificationAdapter = new NotificationAdapter(GenealogyApplication.getInstance(), this, new ArrayList<Notification>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GenealogyApplication.getInstance());
        rcvNotifications.setLayoutManager(mLayoutManager);
        rcvNotifications.setItemAnimator(new DefaultItemAnimator());
        rcvNotifications.setAdapter(mNotificationAdapter);
    }

    public ProgressDialog initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @OnClick({R.id.deathAnniversary})
    public void onClick(View v) {
        if (Utils.isDoubleClick()) return;
        switch (v.getId()) {
            case R.id.deathAnniversary:
                mNotificationAdapter.updateNotifications(mListNotifications,NOTIFICATION_TYPE.ALL);
                break;
        }
    }

    public enum NOTIFICATION_TYPE {
        ALL, DEATH, BIRTHDAY, JOIN, FAMILY
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

    @Override
    public void getListNotifications(List<Notification> notifications) {
        mListNotifications = notifications;
        mNotificationAdapter.updateNotifications(mListNotifications, NOTIFICATION_TYPE.ALL);
    }
}
