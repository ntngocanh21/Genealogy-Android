package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import com.senior.project.genealogy.response.People;
import java.util.List;

public class MapFragmentPresenterImpl implements MapFragmentPresenter {

    private MapModel mMapModel;
    private MapFragmentView mMapFragmentView;

    public MapFragmentPresenterImpl(MapFragmentView mapFragmentView) {
        mMapFragmentView = mapFragmentView;
        mMapModel = new MapModelImpl(this);
    }

    @Override
    public void showToast(String s) {
        mMapFragmentView.closeProgressDialog();
        mMapFragmentView.showToast(s);
    }

    @Override
    public void getFamilyTreeByBranchId(int branchId, String token) {
        mMapFragmentView.showProgressDialog();
        mMapModel.getFamilyTreeByBranchId(branchId, token);
    }

    @Override
    public void getFamilyTreeByBranchIdSuccess(List<People> peopleList) {
        mMapFragmentView.closeProgressDialog();
        mMapFragmentView.showMap(peopleList);
    }

    @Override
    public void getFamilyTreeByBranchIdFalse() {
        mMapFragmentView.closeProgressDialog();
        mMapFragmentView.showToast("False");
    }

    @Override
    public void deletePeople(int peopleId, String token) {
        mMapFragmentView.showProgressDialog();
        mMapModel.deletePeople(peopleId, token);
    }

    @Override
    public void deletePeopleSuccess(int peopleId) {
        mMapFragmentView.closeProgressDialog();
        mMapFragmentView.deletePeople(peopleId);
    }

    @Override
    public void deletePeopleFalse() {
        mMapFragmentView.closeProgressDialog();
    }
}
