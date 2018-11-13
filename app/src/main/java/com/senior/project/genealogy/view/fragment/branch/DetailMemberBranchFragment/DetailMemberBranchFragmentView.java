package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.response.User;
import java.util.List;

public interface DetailMemberBranchFragmentView {
    void showProgressDialog();
    void closeProgressDialog();
    void showToast(String msg);
    void showMember(List<User> userList);
}
