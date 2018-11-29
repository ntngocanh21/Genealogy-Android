package com.senior.project.genealogy.view.fragment.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragment extends Fragment implements NotificationFragmentView {

    @BindView(R.id.rcvNotifications)
    RecyclerView rcvNotifications;

    public NotificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
