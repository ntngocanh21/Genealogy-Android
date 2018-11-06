package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItemRequestMemberAdapter extends RecyclerView.Adapter<RecyclerViewItemRequestMemberAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<User> mUser = new ArrayList<>();

    public RecyclerViewItemRequestMemberAdapter(Context mContext, FragmentManager mFragmentManager, List<User> mUser) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.mUser = mUser;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_member_request, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final String memberName = mUser.get(position).getFullname();
        final String memberUsername = mUser.get(position).getUsername();

        holder.txtName.setText(memberName);
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public void removeItem(int position) {
        mUser.remove(position);
        notifyItemRemoved(position);
    }

    public void updateBranch(User user) {
        mUser.add(user);
        notifyDataSetChanged();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        FrameLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            line = (FrameLayout) itemView.findViewById(R.id.lineBranch);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String username);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            if (type == HomeActivity.PushFrgType.REPLACE) {
                ft.replace(mContainerId, fragment, tag);
                ft.addToBackStack(fragment.getTag());
                ft.commitAllowingStateLoss();
            } else if (type == HomeActivity.PushFrgType.ADD) {
                ft.add(mContainerId, fragment, tag);
                ft.addToBackStack(fragment.getTag());
                ft.commit();
            }
            mFragmentManager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
