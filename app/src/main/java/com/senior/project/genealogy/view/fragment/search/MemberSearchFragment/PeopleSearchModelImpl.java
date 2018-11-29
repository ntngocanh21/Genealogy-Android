package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import com.senior.project.genealogy.response.GenealogyResponse;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.response.PeopleResponse;
import com.senior.project.genealogy.response.Search;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.SearchApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleSearchModelImpl implements PeopleSearchModel {
    private PeopleSearchFragmentPresenter mPeopleSearchFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public PeopleSearchModelImpl(PeopleSearchFragmentPresenter geopleSearchFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mPeopleSearchFragmentPresenter = geopleSearchFragmentPresenter;
    }

    @Override
    public void searchBranchByPeople(People people, String token) {
        Call<PeopleResponse> call = mApplicationApi.getClient().create(SearchApi.class).searchBranchByPeople(people, token);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                PeopleResponse peopleResponse = response.body();
                int code = Integer.parseInt(peopleResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mPeopleSearchFragmentPresenter.searchBranchByPeopleSuccess(peopleResponse.getPeopleList());
                        break;
                    default:
                        mPeopleSearchFragmentPresenter.searchBranchByPeopleFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                mPeopleSearchFragmentPresenter.searchBranchByPeopleFalse();
            }
        });
    }

}
