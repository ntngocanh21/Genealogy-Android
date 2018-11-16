package com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.familyTree.MapFragment.MapFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailInformationBranchFragment extends Fragment implements DetailInformationBranchFragmentView {

    @BindView(R.id.txtBranchName)
    TextView txtBranchName;

    @BindView(R.id.txtBranchDate)
    TextView txtBranchDate;

    @BindView(R.id.txtPeople)
    TextView txtPeople;

    @BindView(R.id.btnFamilyTree)
    ImageButton btnFamilyTree;

    @BindView(R.id.btnEditBranch)
    FloatingActionButton btnEditBranch;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    private Branch branch;
    public DetailInformationBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_information, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        branch = (Branch) getArguments().getSerializable("branch");
        showBranch(branch);
    }

    @OnClick({R.id.btnFamilyTree, R.id.btnEditBranch})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch(view.getId())
        {
            case R.id.btnFamilyTree:
                MapFragment mFragment = new MapFragment();
                bundle.putInt("branchId", branch.getId());
                mFragment.setArguments(bundle);
                pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.detail_branch_frame);
                break;

            case R.id.btnEditBranch:
                break;
        }
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
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
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showBranch(Branch branch) {
        txtBranchName.setText(branch.getName());
        txtDescription.setText(branch.getDescription());
        txtPeople.setText(branch.getMember().toString());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String branchDate = formatter.format(branch.getDate());
        txtBranchDate.setText(branchDate);
    }
}
