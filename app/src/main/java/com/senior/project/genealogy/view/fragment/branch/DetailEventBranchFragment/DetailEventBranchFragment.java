package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.app.GenealogyApplication;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.util.Utils;
import com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.dialog.DialogEventFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailEventBranchFragment extends Fragment implements DetailEventBranchView {

    @BindView(R.id.btnCreateNotification)
    Button btnCreateNotification;

    @BindView(R.id.rcvEvent)
    RecyclerView rcvMember;

    private Branch branch;
    private ProgressDialog mProgressDialog;
    private DetailEventBranchPresenter mDetailEventBranchPresenter;

    public DetailEventBranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_event, container, false);
        ButterKnife.bind(this, view);
        mDetailEventBranchPresenter = new DetailEventBranchPresenterImpl(this);
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
        final DialogEventFragment dialogEventFragment = DialogEventFragment.newInstance();
        dialogEventFragment.show(getActivity().getSupportFragmentManager(), null);

        dialogEventFragment.attackInterface(new DialogEventFragment.DialogEventInterface() {
            @Override
            public void sendDataToFrg(Event event) {
                dialogEventFragment.dismiss();
                event.setBranchId(branch.getId());
                String userName = GenealogyApplication.getInstance().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_KEY.USERNAME, Constants.EMPTY_STRING);
                event.setUserName(userName);
                mDetailEventBranchPresenter.pushEvent(event);
            }
        });
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (!getActivity().isFinishing() && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public ProgressDialog initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }
}
