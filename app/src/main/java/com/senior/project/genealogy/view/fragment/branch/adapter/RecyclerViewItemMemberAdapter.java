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

public class RecyclerViewItemMemberAdapter extends RecyclerView.Adapter<RecyclerViewItemMemberAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<User> mUser = new ArrayList<>();

    public RecyclerViewItemMemberAdapter(Context mContext, FragmentManager mFragmentManager, List<User> mUser) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.mUser = mUser;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_member, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final String memberName = mUser.get(position).getFullname();
        final String memberUsername = mUser.get(position).getUsername();
        final String memberRole = mUser.get(position).getRole();

        holder.txtName.setText(memberName);
        showSpinner(holder.spRole);
        if("Admin".equals(memberRole)){
            holder.spRole.setSelection(0);
        } else {
            holder.spRole.setSelection(1);
        }

    }


    public void showSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(mContext, R.array.role_member_array, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spinner.setAdapter(dataAdapter);
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
        Spinner spRole;
        FrameLayout line;
        RelativeLayout viewBackground, viewForeground;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            spRole = (Spinner) itemView.findViewById(R.id.spRole);
            line = (FrameLayout) itemView.findViewById(R.id.lineBranch);
            viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewBackgroundBranch);
            viewForeground = (RelativeLayout)itemView.findViewById(R.id.viewForegroundBranch);
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
