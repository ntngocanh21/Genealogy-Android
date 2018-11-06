package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;


import com.senior.project.genealogy.response.User;

import java.util.List;

public interface DetailMemberRequestBranchFragmentView {
    void showProgressDialog();
    void closeProgressDialog();
    void showRequestMember(List<User> userList);
}
