package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.dialog.DialogEventFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailEventBranchFragment extends Fragment implements DetailEventBranchFragmentView {

    @BindView(R.id.btnCreateNotification)
    Button btnCreateNotification;

    @BindView(R.id.rcvEvent)
    RecyclerView rcvMember;

    private Branch branch;

    public DetailEventBranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_event, container, false);
        ButterKnife.bind(this, view);
        branch = (Branch) getArguments().getSerializable("branch");
        if (branch.getRole() == Constants.ROLE.ADMIN_ROLE || branch.getRole() == Constants.ROLE.MOD_ROLE){
            btnCreateNotification.setVisibility(View.VISIBLE);
        }else {
            btnCreateNotification.setVisibility(View.GONE);
        }
        return view;
    }

    @OnClick(R.id.btnCreateNotification)
    public void onClick() {
        if (Utils.isDoubleClick()) return;
        DialogEventFragment dialogEventFragment = DialogEventFragment.newInstance();
        dialogEventFragment.show(getActivity().getSupportFragmentManager(), null);

        dialogEventFragment.attackInterface(new DialogEventFragment.DialogEventInterface() {
            @Override
            public void sendDataToFrg(Event event) {

            }
        });
    }
}
