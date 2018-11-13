package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment.DetailMemberBranchFragmentPresenter;

import java.util.List;

public class RecyclerViewItemMemberAdapter extends RecyclerView.Adapter<RecyclerViewItemMemberAdapter.RecyclerViewHolder>{
    private Context mContext;
    private List<User> mUser;
    private int branchId;
    private DetailMemberBranchFragmentPresenter mDetailMemberBranchFragmentPresenter;

    private String token;

    public RecyclerViewItemMemberAdapter(Context mContext, List<User> mUser, int branchId, DetailMemberBranchFragmentPresenter mDetailMemberBranchFragmentPresenter) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.branchId = branchId;
        this.mDetailMemberBranchFragmentPresenter = mDetailMemberBranchFragmentPresenter;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_member, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        final String memberName = mUser.get(position).getFullname();
        final String memberUsername = mUser.get(position).getUsername();
        final String memberRole = mUser.get(position).getRole();

        holder.txtName.setText(memberName);
        showSpinner(holder.spRole, mUser.get(position));
        if("Admin".equals(memberRole)){
            holder.spRole.setSelection(0);
        } else {
            holder.spRole.setSelection(1);
        }

    }


    public void showSpinner(final Spinner spinner, final User user) {
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(mContext, R.array.role_member_array, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                int roleId = 0;
                String role = spinner.getSelectedItem().toString();
                switch (role){
                    case "Admin":
                        roleId = 2;
                        break;
                    case "Member":
                        roleId = 3;
                        break;
                }

                UserBranchPermission userBranchPermission = new UserBranchPermission(user.getUsername(), branchId, roleId);
                mDetailMemberBranchFragmentPresenter.changeRoleMemberOfBranch(token, userBranchPermission);
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

    public void updateMember(User user) {
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

}
