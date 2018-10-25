package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.CodeResponse;
import com.senior.project.genealogy.response.FamilyTreeResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.FamilyTreeApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapModelImpl implements MapModel {
    private MapFragmentPresenter mMapFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public MapModelImpl(MapFragmentPresenter mapFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mMapFragmentPresenter = mapFragmentPresenter;
    }

    @Override
    public void getFamilyTreeByBranchId(int branchId, String token) {
        Call<FamilyTreeResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).getPeopleByBranchId(branchId, token);
        call.enqueue(new Callback<FamilyTreeResponse>() {
            @Override
            public void onResponse(Call<FamilyTreeResponse> call, Response<FamilyTreeResponse> response) {
                FamilyTreeResponse familyTreeResponse = response.body();
                int code = Integer.parseInt(familyTreeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mMapFragmentPresenter.getFamilyTreeByBranchIdSuccess(familyTreeResponse.getPeopleList());
                        break;
                    default:
                        mMapFragmentPresenter.getFamilyTreeByBranchIdFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FamilyTreeResponse> call, Throwable t) {
                mMapFragmentPresenter.getFamilyTreeByBranchIdFalse();
            }
        });
    }

    @Override
    public void deletePeople(final int peopleId, String token) {
        Call<CodeResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).deletePeople(peopleId, token);
        call.enqueue(new Callback<CodeResponse>() {
            @Override
            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                CodeResponse codeResponse = response.body();
                int code = Integer.parseInt(codeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mMapFragmentPresenter.deletePeopleSuccess(peopleId);
                        break;
                    default:
                        mMapFragmentPresenter.deletePeopleFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<CodeResponse> call, Throwable t) {
                mMapFragmentPresenter.deletePeopleFalse();
            }
        });
    }
}
