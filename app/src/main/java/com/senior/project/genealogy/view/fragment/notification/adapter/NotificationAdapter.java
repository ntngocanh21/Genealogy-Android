package com.senior.project.genealogy.view.fragment.notification.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Notification> mNotification;
    private FragmentManager mFragment;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent, tvDate;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDate = view.findViewById(R.id.tvDate);
            tvContent = view.findViewById(R.id.tvContent);
        }
    }

    public NotificationAdapter(Context context, FragmentManager fragment, List<Notification> notification) {
        mContext = context;
        mFragment = fragment;
        mNotification = notification;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Notification item = mNotification.get(position);
        holder.tvTitle.setText(item.getTitle());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = df.format(item.getDate());
        holder.tvDate.setText(date);
        holder.tvContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mNotification.size();
    }

}
