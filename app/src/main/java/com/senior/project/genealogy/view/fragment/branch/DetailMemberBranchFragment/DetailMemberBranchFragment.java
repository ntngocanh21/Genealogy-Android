package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMemberBranchFragment extends Fragment implements DetailMemberBranchFragmentView{

    @BindView(R.id.btnCreateNotification)
    Button btnCreateNotification;

    @BindView(R.id.rcvMember)
    RecyclerView rcvMember;

    public DetailMemberBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_member, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
