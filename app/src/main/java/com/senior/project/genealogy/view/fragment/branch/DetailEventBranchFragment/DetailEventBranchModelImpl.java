package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;


import android.content.Context;

import com.senior.project.genealogy.app.GenealogyApplication;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.response.EventResponse;
import com.senior.project.genealogy.service.ApplicationApi;
import com.senior.project.genealogy.service.UserApi;
import com.senior.project.genealogy.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventBranchModelImpl implements  DetailEventBranchModel {

    private DetailEventBranchPresenter mDetailEventBranchPresenter;
    private ApplicationApi mApplicationApi;

    public DetailEventBranchModelImpl(DetailEventBranchPresenterImpl detailEventBranchPresenter) {
        if (mApplicationApi == null) {
            mApplicationApi = new ApplicationApi();
        }
        mDetailEventBranchPresenter = detailEventBranchPresenter;
    }

    @Override
    public void pushEvent(Event event) {
        String token = GenealogyApplication.getInstance().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, Constants.EMPTY_STRING);
        Call<EventResponse> call = mApplicationApi.getClient().create(UserApi.class).pushEvent(token, event);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                int code = Integer.parseInt(eventResponse.getError().getCode());
                switch (code){
                    case Constants.HTTPCodeResponse.SUCCESS:
                        break;
                    case Constants.HTTPCodeResponse.OBJECT_NOT_FOUND:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {

            }
        });
    }
}
