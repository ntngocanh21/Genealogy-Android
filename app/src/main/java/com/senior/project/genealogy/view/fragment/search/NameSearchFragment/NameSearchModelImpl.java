package com.senior.project.genealogy.view.fragment.search.NameSearchFragment;

import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.GenealogyApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameSearchModelImpl implements NameSearchModel {
    private NameSearchFragmentPresenter mNameSearchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public NameSearchModelImpl(NameSearchFragmentPresenter nameSearchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mNameSearchFragmentPresenter = nameSearchFragmentPresenter;
    }

    @Override
    public void getGenealogies(String token) {
        Call<GenealogyResponse> call = mApplicationApi.getClient().create(GenealogyApi.class).getGenealogies(token);
        call.enqueue(new Callback<GenealogyResponse>() {
            @Override
            public void onResponse(Call<GenealogyResponse> call, Response<GenealogyResponse> response) {
                GenealogyResponse genealogyResponse = response.body();
                int code = Integer.parseInt(genealogyResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mNameSearchFragmentPresenter.getGenealogiesSuccess(genealogyResponse.getGenealogyList());
                        break;
                    default:
                        mNameSearchFragmentPresenter.getGenealogiesFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<GenealogyResponse> call, Throwable t) {
                mNameSearchFragmentPresenter.getGenealogiesFalse();
            }
        });
    }

}
