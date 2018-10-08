package com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment;



import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.CreateGenealogyFragmentPresenter;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.GenealogyModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailGenealogyModelImpl implements DetailGenealogyModel {
    private DetailGenealogyFragmentPresenter mDetailGenealogyFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public DetailGenealogyModelImpl(DetailGenealogyFragmentPresenter detailGenealogyFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailGenealogyFragmentPresenter = detailGenealogyFragmentPresenter;
    }
}
