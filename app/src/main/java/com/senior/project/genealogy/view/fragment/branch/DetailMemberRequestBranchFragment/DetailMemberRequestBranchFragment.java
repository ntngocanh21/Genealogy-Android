package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.branch.adapter.RecyclerViewItemRequestMemberAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMemberRequestBranchFragment extends Fragment implements DetailMemberRequestBranchFragmentView{

    @BindView(R.id.rcvMemberRequest)
    RecyclerView rcvMemberRequest;

    private Branch branch;
    private String token;
    private List<User> users;
    private ProgressDialog mProgressDialog;
    private RecyclerViewItemRequestMemberAdapter mRcvAdapter;
    private DetailMemberRequestBranchFragmentPresenterImpl detailMemberRequestBranchFragmentPresenterImpl;

    public DetailMemberRequestBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_member_request, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        branch = (Branch) getArguments().getSerializable("branch");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        UserBranchPermission userBranchPermission = new UserBranchPermission(false, branch.getId());
        detailMemberRequestBranchFragmentPresenterImpl = new DetailMemberRequestBranchFragmentPresenterImpl(this);
        detailMemberRequestBranchFragmentPresenterImpl.getRequestMemberOfBranch(token, userBranchPermission);
    }

    @Override
    public void showRequestMember(List<User> userList) {
        users = new ArrayList<>();

        if(userList == null){
//            showToast("You didn't have any member");
        }
        else {
            users.addAll(userList);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemRequestMemberAdapter(getActivity(), fragmentManager, users);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvMemberRequest.setLayoutManager(layoutManager);
        rcvMemberRequest.setAdapter(mRcvAdapter);
    }

    public ProgressDialog initProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

}
