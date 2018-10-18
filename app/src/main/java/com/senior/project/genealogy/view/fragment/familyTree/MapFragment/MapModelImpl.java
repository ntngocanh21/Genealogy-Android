package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

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
}
