package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public class DetailMemberBranchFragmentPresenterImpl implements DetailMemberBranchFragmentPresenter {

    private DetailMemberBranchModel mDetailMemberBranchModel;
    private DetailMemberBranchFragmentView mDetailMemberBranchFragmentView;

    public DetailMemberBranchFragmentPresenterImpl(DetailMemberBranchFragmentView detailMemberBranchFragmentView) {
        mDetailMemberBranchFragmentView = detailMemberBranchFragmentView;
        mDetailMemberBranchModel = new DetailMemberBranchModelImpl(this);
    }

    @Override
    public void getMemberOfBranch(String token, UserBranchPermission userBranchPermission) {
        mDetailMemberBranchFragmentView.showProgressDialog();
        mDetailMemberBranchModel.getMemberOfBranch(userBranchPermission, token);
    }

    @Override
    public void getMemberOfBranchSuccess(List<User> userList) {
        mDetailMemberBranchFragmentView.closeProgressDialog();
        mDetailMemberBranchFragmentView.showMember(userList);
    }

    @Override
    public void getMemberOfBranchFalse() {
        mDetailMemberBranchFragmentView.closeProgressDialog();
    }
}
