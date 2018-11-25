package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.Search;

import java.util.List;

public interface GenealogySearchFragmentPresenter {
    void getGenealogies(String token);
    void getGenealogiesSuccess(List<Genealogy> genealogyList);
    void getGenealogiesFalse();
    void searchGenealogyByName(Search search, String token);
    void searchGenealogyByNameSuccess(List<Genealogy> genealogyList);
    void searchGenealogyByNameFalse();
    void showToast(String s);
}
