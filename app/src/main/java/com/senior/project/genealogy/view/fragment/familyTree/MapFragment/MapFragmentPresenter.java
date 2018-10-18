package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.People;
import java.util.List;

public interface MapFragmentPresenter {
    void showToast(String s);
    void getFamilyTreeByBranchId(int branchId, String token);
    void getFamilyTreeByBranchIdSuccess(List<People> peopleList);
    void getFamilyTreeByBranchIdFalse();
}
