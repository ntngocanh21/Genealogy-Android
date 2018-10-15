package com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment;

import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyModel;


public class DetailInformationBranchModelImpl implements DetailInformationBranchModel {
    private DetailInformationBranchFragmentPresenter mDetailInformationBranchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailInformationBranchModelImpl(DetailInformationBranchFragmentPresenter detailInformationBranchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailInformationBranchFragmentPresenter = detailInformationBranchFragmentPresenter;
    }
}
