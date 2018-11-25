package com.senior.project.genealogy.view.fragment.search.GenealogySearchFragment;

import com.senior.project.genealogy.response.Search;

public interface GenealogySearchModel {
    void getGenealogies(String token);
    void searchGenealogyByName(Search search, String token);
   // void searchBranchByName(Search search, String token);
}
