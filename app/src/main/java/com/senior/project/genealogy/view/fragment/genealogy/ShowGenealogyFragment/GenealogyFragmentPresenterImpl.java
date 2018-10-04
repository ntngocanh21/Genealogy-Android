package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;

import java.util.List;

public class GenealogyFragmentPresenterImpl implements GenealogyFragmentPresenter {

    private GenealogyModel mGenealogyModel;
    private GenealogyFragmentView mGenealogyFragmentView;

    public GenealogyFragmentPresenterImpl(GenealogyFragmentView genealogyFragmentView) {
        mGenealogyFragmentView = genealogyFragmentView;
        mGenealogyModel = new GenealogyModelImpl(this);
    }

    @Override
    public void getGenealogiesByUsername(String token) {
        mGenealogyFragmentView.showProgressDialog();
        mGenealogyModel.getGenealogiesByUsername(token);
    }

    @Override
    public void getGenealogiesByUsernameSuccess(List<Genealogy> genealogyList) {
        mGenealogyFragmentView.closeProgressDialog();
        mGenealogyFragmentView.showGenealogy(genealogyList);
    }

    @Override
    public void getGenealogiesByUsernameFalse() {
        mGenealogyFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mGenealogyFragmentView.closeProgressDialog();
        mGenealogyFragmentView.showToast(s);
    }
}
