package com.senior.project.genealogy.view.fragment.search.MemberSearchFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PeopleSearchFragment extends Fragment {

//    @BindView(R.id.viewpager)
//    ViewPager mViewPager;
//
//    @BindView(R.id.tabs)
//    TabLayout mTabLayout;

    public PeopleSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_people, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
