package com.senior.project.genealogy.view.fragment.search.GenealogySearchResultFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface GenealogySearchResultFragmentView {
    void showGenealogy(List<Genealogy> genealogyList);
    void showToast(String msg);
    void showProgressDialog();
    void closeProgressDialog();
}
