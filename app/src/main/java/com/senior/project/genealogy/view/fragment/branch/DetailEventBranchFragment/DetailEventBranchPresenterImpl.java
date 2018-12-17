package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment;


import com.senior.project.genealogy.response.Event;

public class DetailEventBranchPresenterImpl implements DetailEventBranchPresenter {

    private DetailEventBranchView mDetailEventBranchView;
    private DetailEventBranchModel mDetailEventBranchModel;

    public DetailEventBranchPresenterImpl(DetailEventBranchView detailEventBranchView) {
        mDetailEventBranchView = detailEventBranchView;
        mDetailEventBranchModel = new DetailEventBranchModelImpl(this);
    }

    @Override
    public void pushEvent(Event event) {
        if (mDetailEventBranchView != null) {
            mDetailEventBranchView.showProgressDialog();
            mDetailEventBranchModel.pushEvent(event);
        }
    }
}
