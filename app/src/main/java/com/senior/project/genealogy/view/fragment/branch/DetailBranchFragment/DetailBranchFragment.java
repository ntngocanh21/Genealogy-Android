package com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment.DetailInformationBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment.DetailMemberBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.adapter.SectionsPageAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailBranchFragment extends Fragment implements DetailBranchFragmentView{

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private Branch branch;

    public DetailBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_detail, container, false);
        ButterKnife.bind(this, view);
        branch = (Branch) getArguments().getSerializable("branch");
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        DetailInformationBranchFragment fragmentInfo = new DetailInformationBranchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("branch", branch);
        fragmentInfo.setArguments(bundle);
        adapter.addFragment(fragmentInfo, "Information");

        adapter.addFragment(new DetailMemberBranchFragment(), "Member");
        viewPager.setAdapter(adapter);
    }

}
