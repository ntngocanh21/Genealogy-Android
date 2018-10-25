package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

public interface MapModel {
    void getFamilyTreeByBranchId(int branchId, String token);
    void deletePeople(int peopleId, String token);
}
