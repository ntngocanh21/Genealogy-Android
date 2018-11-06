package com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment;

import com.senior.project.genealogy.response.UserBranchPermission;

public interface DetailMemberRequestBranchModel {
    void getRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token);
    void declineRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token, int position);
    void acceptRequestMemberOfBranch(UserBranchPermission userBranchPermission, String token, int position);
}
