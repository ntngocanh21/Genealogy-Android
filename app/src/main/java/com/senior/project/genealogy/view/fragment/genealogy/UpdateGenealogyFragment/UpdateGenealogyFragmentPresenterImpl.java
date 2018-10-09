package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragmentView;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyModel;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyModelImpl;

import java.util.List;

public class UpdateGenealogyFragmentPresenterImpl implements UpdateGenealogyFragmentPresenter {

    private UpdateGenealogyModel mUpdateGenealogyModel;
    private UpdateGenealogyFragmentView mUpdateGenealogyFragmentView;

    public UpdateGenealogyFragmentPresenterImpl(UpdateGenealogyFragmentView updateGenealogyFragmentView) {
        mUpdateGenealogyFragmentView = updateGenealogyFragmentView;
        mUpdateGenealogyModel = new UpdateGenealogyModelImpl(this);
    }

    @Override
    public void updateGenealogy(Genealogy genealogy, String token) {
        mUpdateGenealogyFragmentView.showProgressDialog();
        mUpdateGenealogyModel.updateGenealogy(genealogy, token);
    }

    @Override
    public void updateGenealogySuccess() {
        mUpdateGenealogyFragmentView.closeProgressDialog();
    }

    @Override
    public void updateGenealogyFalse() {
        mUpdateGenealogyFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mUpdateGenealogyFragmentView.closeProgressDialog();
        mUpdateGenealogyFragmentView.showToast(s);
    }
}
