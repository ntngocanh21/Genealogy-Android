package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public interface UpdateGenealogyFragmentPresenter {
    void updateGenealogy(Genealogy genealogy, String token);
    void updateGenealogySuccess();
    void updateGenealogyFalse();
    void showToast(String s);
}
