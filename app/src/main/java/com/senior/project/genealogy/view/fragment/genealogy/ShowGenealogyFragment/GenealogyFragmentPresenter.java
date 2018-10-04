package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface GenealogyFragmentPresenter {
    void getGenealogiesByUsername(String token);
    void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList);
    void getGenealogiesByUsernameFalse();
    void showToast(String s);
}
