package com.senior.project.genealogy.view.fragment.search.NameSearchFragment;

import android.support.v7.widget.RecyclerView;

import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragmentView;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyModel;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyModelImpl;

import java.util.List;

public class NameSearchFragmentPresenterImpl implements NameSearchFragmentPresenter {

    private NameSearchModel mNameSearchModel;
    private NameSearchFragmentView mNameSearchFragmentView;

    public NameSearchFragmentPresenterImpl(NameSearchFragmentView nameSearchFragmentView) {
        mNameSearchFragmentView = nameSearchFragmentView;
        mNameSearchModel = new NameSearchModelImpl(this);
    }

    @Override
    public void getGenealogies(String token) {
        mNameSearchFragmentView.showProgressDialog();
        mNameSearchModel.getGenealogies(token);
    }

    @Override
    public void getGenealogiesSuccess(List<Genealogy> genealogyList) {
        mNameSearchFragmentView.closeProgressDialog();
        mNameSearchFragmentView.showGenealogy(genealogyList);
    }

    @Override
    public void getGenealogiesFalse() {
        mNameSearchFragmentView.closeProgressDialog();
    }

    @Override
    public void showToast(String s) {
        mNameSearchFragmentView.closeProgressDialog();
        mNameSearchFragmentView.showToast(s);
    }

}
