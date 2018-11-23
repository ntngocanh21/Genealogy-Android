package com.senior.project.genealogy.view.fragment.search.NameSearchFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface NameSearchFragmentPresenter {
    void getGenealogies(String token);
    void getGenealogiesSuccess(List<Genealogy> genealogyList);
    void getGenealogiesFalse();
    void showToast(String s);
}
