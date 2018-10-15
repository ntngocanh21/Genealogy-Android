package com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment;

import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyModel;


public class DetailMemberBranchModelImpl implements DetailGenealogyModel {
    private DetailGenealogyFragmentPresenter mDetailGenealogyFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailMemberBranchModelImpl(DetailGenealogyFragmentPresenter detailGenealogyFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailGenealogyFragmentPresenter = detailGenealogyFragmentPresenter;
    }
}
