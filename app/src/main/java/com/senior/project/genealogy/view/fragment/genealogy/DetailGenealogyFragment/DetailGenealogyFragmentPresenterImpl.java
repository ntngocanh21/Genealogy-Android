package com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment;


import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.CreateGenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.CreateGenealogyFragmentView;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.GenealogyModel;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.GenealogyModelImpl;

import java.util.List;

public class DetailGenealogyFragmentPresenterImpl implements DetailGenealogyFragmentPresenter {

    private DetailGenealogyModel mDetailGenealogyModel;
    private DetailGenealogyFragmentView mDetailGenealogyFragmentView;

    public DetailGenealogyFragmentPresenterImpl(DetailGenealogyFragmentView detailGenealogyFragmentView) {
        mDetailGenealogyFragmentView = detailGenealogyFragmentView;
        mDetailGenealogyModel = new DetailGenealogyModelImpl(this);
    }

}
