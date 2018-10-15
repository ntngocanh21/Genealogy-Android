package com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailInformationBranchFragment extends Fragment implements DetailInformationBranchFragmentView{

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

}
