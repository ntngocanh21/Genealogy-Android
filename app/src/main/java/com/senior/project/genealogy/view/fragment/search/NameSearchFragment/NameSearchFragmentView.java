package com.senior.project.genealogy.view.fragment.search.NameSearchFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface NameSearchFragmentView {
    void showToast(String msg);
    void showGenealogy(List<Genealogy> genealogyList);
    void showProgressDialog();
    void closeProgressDialog();
}
