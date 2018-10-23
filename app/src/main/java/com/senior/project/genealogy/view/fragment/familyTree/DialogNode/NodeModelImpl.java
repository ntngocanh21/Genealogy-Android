package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import com.senior.project.genealogy.response.FamilyTreeResponse;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.FamilyTreeApi;
import com.senior.project.genealogy.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodeModelImpl implements NodeModel {
    private DialogNodeFragmentPresenter mDialogNodeFragmentPresenter;
    private ApplicationApi mApplicationApi;

    public NodeModelImpl(DialogNodeFragmentPresenter dialogNodeFragmentPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDialogNodeFragmentPresenter = dialogNodeFragmentPresenter;
    }

    @Override
    public void createPeople(People people, String token) {
        Call<FamilyTreeResponse> call = mApplicationApi.getClient().create(FamilyTreeApi.class).createPeople(people, token);
        call.enqueue(new Callback<FamilyTreeResponse>() {
            @Override
            public void onResponse(Call<FamilyTreeResponse> call, Response<FamilyTreeResponse> response) {
                FamilyTreeResponse familyTreeResponse = response.body();
                int code = Integer.parseInt(familyTreeResponse.getError().getCode());
                switch (code) {
                    case Constants.HTTPCodeResponse.SUCCESS:
                        mDialogNodeFragmentPresenter.createPeopleSuccess(familyTreeResponse.getPeopleList());
                        break;
                    default:
                        mDialogNodeFragmentPresenter.createPeopleFalse();
                        break;
                }
            }

            @Override
            public void onFailure(Call<FamilyTreeResponse> call, Throwable t) {
                mDialogNodeFragmentPresenter.createPeopleFalse();
            }
        });
    }
}
