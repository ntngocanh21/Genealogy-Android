package com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment;


import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyFragmentView;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyModel;
import com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment.DetailGenealogyModelImpl;

public class DetailInformationBranchFragmentPresenterImpl implements DetailInformationBranchFragmentPresenter {

    private DetailInformationBranchModel mDetailInformationBranchModel;
    private DetailInformationBranchFragmentView mDetailInformationBranchFragmentView;

    public DetailInformationBranchFragmentPresenterImpl(DetailInformationBranchFragmentView detailInformationBranchFragmentView) {
        mDetailInformationBranchFragmentView = detailInformationBranchFragmentView;
        mDetailInformationBranchModel = new DetailInformationBranchModelImpl(this);
    }

}
