package com.senior.project.genealogy.view.fragment.search.NameSearchResultFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface NameSearchResultFragmentView {
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
    void showGenealogyAndBranch(List<Genealogy> genealogyList, List<Branch> branchList);
}
