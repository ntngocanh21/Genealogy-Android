package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Notification;
import com.senior.project.genealogy.view.fragment.notification.NotificationFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Notification> mGrNotifications;
    private NotificationFragment mFragment;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvRuleID, tvContent;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTopic);
            tvRuleID = view.findViewById(R.id.tvRuleID);
            tvContent = view.findViewById(R.id.tvApproach);
        }
    }

    public NotificationAdapter(Context context, NotificationFragment fragment, List<Notification> grouNotifications) {
        mContext = context;
        mFragment = fragment;
        mGrNotifications = grouNotifications;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Notification item = mGrNotifications.get(position);
        holder.tvRuleID.setText(String.valueOf(item.getId()));
        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mGrNotifications.size();
    }

    public void updateNotifications(List<Notification> rules, NotificationFragment.NOTIFICATION_TYPE type) {
        if (type == NotificationFragment.NOTIFICATION_TYPE.ALL) {
            mGrNotifications = rules;
        } else {
            List<Notification> temps = new ArrayList<>();
            for (int j = 0; j < mGrNotifications.size(); j++) {
                if (Integer.parseInt(mGrNotifications.get(j).getType()) == 3 && type == NotificationFragment.NOTIFICATION_TYPE.BIRTHDAY) {
                    temps.add(mGrNotifications.get(j));
                } else if (Integer.parseInt(mGrNotifications.get(j).getType()) == 4 && type == NotificationFragment.NOTIFICATION_TYPE.DEATH) {
                    temps.add(mGrNotifications.get(j));
                } else if (Integer.parseInt(mGrNotifications.get(j).getType()) == 5 && type == NotificationFragment.NOTIFICATION_TYPE.FAMILY) {
                    temps.add(mGrNotifications.get(j));
                }
            }
            mGrNotifications = temps;
        }
        notifyDataSetChanged();
    }
}
