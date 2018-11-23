package com.senior.project.genealogy.view.fragment.search.NameSearchFragment;

import com.senior.project.genealogy.response.Search;

public interface NameSearchModel {
    void getGenealogies(String token);
    void searchGenealogyByName(Search search, String token);
}
