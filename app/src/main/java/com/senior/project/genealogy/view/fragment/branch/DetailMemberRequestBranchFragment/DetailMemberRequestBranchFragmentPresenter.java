package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.response.UserBranchPermission;

import java.util.List;

public interface DetailMemberRequestBranchFragmentPresenter {
    void getRequestMemberOfBranch(String token, UserBranchPermission userBranchPermission);
    void getRequestMemberOfBranchSuccess(List<User> userList);
    void getRequestMemberOfBranchFalse();
}
