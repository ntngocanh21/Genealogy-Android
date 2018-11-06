package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public class DetailMemberRequestBranchFragmentPresenterImpl implements DetailMemberRequestBranchFragmentPresenter {

    private DetailMemberRequestBranchModel mDetailMemberRequestBranchModel;
    private DetailMemberRequestBranchFragmentView mDetailMemberRequestBranchFragmentView;

    public DetailMemberRequestBranchFragmentPresenterImpl(DetailMemberRequestBranchFragmentView detailMemberRequestBranchFragmentView) {
        mDetailMemberRequestBranchFragmentView = detailMemberRequestBranchFragmentView;
        mDetailMemberRequestBranchModel = new DetailMemberRequestBranchModelImpl(this);
    }

    @Override
    public void getRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission) {
        mDetailMemberRequestBranchFragmentView.showProgressDialog();
        mDetailMemberRequestBranchModel.getRequestMemberOfBranch(userBranchPermission, token);
    }

    @Override
    public void getRequestMemberOfBranchSuccess(List<User> userList) {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
        mDetailMemberRequestBranchFragmentView.showRequestMember(userList);
    }

    @Override
    public void getRequestMemberOfBranchFalse() {
        mDetailMemberRequestBranchFragmentView.closeProgressDialog();
    }
}
