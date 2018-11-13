package com.senior.project.genealogy.view.fragment.search.SearchFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.activity.home.HomeActivity;


public class SearchFragment extends Fragment {

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).updateTitleBar("Search");
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

}
