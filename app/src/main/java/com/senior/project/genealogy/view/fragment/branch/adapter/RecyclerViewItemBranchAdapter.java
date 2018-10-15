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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment.DetailBranchFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewItemBranchAdapter extends RecyclerView.Adapter<RecyclerViewItemBranchAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<Branch> mBranches = new ArrayList<>();

    public RecyclerViewItemBranchAdapter(Context mContext, FragmentManager mFragmentManager, List<Branch> mBranches) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        this.mBranches = mBranches;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_branch, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final String branchName = mBranches.get(position).getName();
        final int member = mBranches.get(position).getMember();
        final Date branchDate = mBranches.get(position).getDate();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final String date = formatter.format(branchDate);

        holder.txtBranchName.setText(branchName);
        holder.txtNumberOfPeople.setText(String.valueOf(member));
        holder.txtBranchDate.setText(date);

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailBranchFragment mFragment = new DetailBranchFragment();
//                Bundle bundle = new Bundle();
//                Genealogy genealogy = new Genealogy(genealogyId, genealogyName, genealogyHistory, genealogyOwner, genealogyDate, genealogyBranch);
//                bundle.putSerializable("genealogy", genealogy);
//                mFragment.setArguments(bundle);
//
//                if(mContext instanceof HomeActivity){
//                    ((HomeActivity) mContext).updateTitleBar("Genealogy Information");
//                }
                pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.branch_frame);
                Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBranches.size();
    }

    public void removeItem(int position) {
        mBranches.remove(position);
        notifyItemRemoved(position);
    }

    public void updateBranch(Branch branch) {
        mBranches.add(branch);
        notifyDataSetChanged();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtBranchName;
        TextView txtNumberOfPeople;
        TextView txtBranchDate;
        FrameLayout line;
        RelativeLayout viewBackground, viewForeground;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtBranchName = (TextView) itemView.findViewById(R.id.txtBranchName);
            txtNumberOfPeople = (TextView) itemView.findViewById(R.id.txtNumberOfPeople);
            txtBranchDate = (TextView) itemView.findViewById(R.id.txtBranchDate);
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
