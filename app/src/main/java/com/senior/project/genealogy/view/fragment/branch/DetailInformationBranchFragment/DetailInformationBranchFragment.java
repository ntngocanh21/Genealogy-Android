package com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.util.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailInformationBranchFragment extends Fragment implements DetailInformationBranchFragmentView {

    @BindView(R.id.txtBranchName)
    TextView txtBranchName;

    @BindView(R.id.txtBranchOwner)
    TextView txtBranchOwner;

    @BindView(R.id.txtBranchDate)
    TextView txtBranchDate;

    @BindView(R.id.txtPeople)
    TextView txtPeople;

    @BindView(R.id.btnFamilyTree)
    Button btnFamilyTree;

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

    @OnClick(R.id.btnEditBranch)
    public void onClick() {

    }

    @Override
    public void onResume() {
        super.onResume();
        branch = (Branch) getArguments().getSerializable("branch");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        showBranch(branch);
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
