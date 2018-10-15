package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;


import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragmentView;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyModel;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyModelImpl;

public class DetailMemberBranchFragmentPresenterImpl implements DetailGenealogyFragmentPresenter {

    private DetailGenealogyModel mDetailGenealogyModel;
    private DetailGenealogyFragmentView mDetailGenealogyFragmentView;

    public DetailMemberBranchFragmentPresenterImpl(DetailGenealogyFragmentView detailGenealogyFragmentView) {
        mDetailGenealogyFragmentView = detailGenealogyFragmentView;
        mDetailGenealogyModel = new DetailGenealogyModelImpl(this);
    }

}
