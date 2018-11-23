package com.senior.project.genealogy.view.fragment.search.SearchFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.search.Adapter.SectionsPageAdapter;
import com.senior.project.genealogy.view.fragment.search.MemberSearchFragment.MemberSearchFragment;
import com.senior.project.genealogy.view.fragment.search.NameSearchFragment.NameSearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private NameSearchFragment mNameSearchFragment;
    private MemberSearchFragment mMemberSearchFragment;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_search));
        ButterKnife.bind(this, view);
        if (mNameSearchFragment == null)
            mNameSearchFragment = new NameSearchFragment();
        if (mMemberSearchFragment == null)
            mMemberSearchFragment = new MemberSearchFragment();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter.TitleStringUtils titleStringUtils = new SectionsPageAdapter.TitleStringUtils(getActivity());
        SectionsPageAdapter adapter;
        int numPage;
        List<Fragment> arrListFrg = new ArrayList<>();
        arrListFrg.add(mNameSearchFragment);
        arrListFrg.add(mMemberSearchFragment);
        numPage = titleStringUtils.gettitlesSearchType().length;
        adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), titleStringUtils.gettitlesSearchType(), arrListFrg);
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(numPage);
    }
}
