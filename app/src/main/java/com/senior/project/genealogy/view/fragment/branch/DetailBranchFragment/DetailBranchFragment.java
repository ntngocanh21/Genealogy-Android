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
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment.DetailInformationBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment.DetailMemberBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberRequestBranchFragment.DetailMemberRequestBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.adapter.SectionsPageAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailBranchFragment extends Fragment implements DetailBranchFragmentView{

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private Branch branch;

    private DetailInformationBranchFragment mInformationBranchFrg;
    private DetailMemberBranchFragment mMemberBranchFrg;
    private DetailMemberRequestBranchFragment mMemberRequestBranchFrg;

    public DetailBranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_detail, container, false);
        ButterKnife.bind(this, view);
        branch = (Branch) getArguments().getSerializable("branch");
        if (mInformationBranchFrg == null)
            mInformationBranchFrg = new DetailInformationBranchFragment();
        if (mMemberBranchFrg == null)
            mMemberBranchFrg = new DetailMemberBranchFragment();
        if (mMemberRequestBranchFrg == null)
            mMemberRequestBranchFrg = new DetailMemberRequestBranchFragment();
        mMemberRequestBranchFrg.attackInterface(new DetailMemberRequestBranchFragment.RequestMemberInterface() {
            @Override
            public void sendDataToListMember(User member) {
                updateMember(member);
            }
        });
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    public void updateMember(User user){
        mMemberBranchFrg.updateMember(user);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter.TitleStringUtils titleStringUtils = new SectionsPageAdapter.TitleStringUtils(getActivity());
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), titleStringUtils, mInformationBranchFrg, mMemberBranchFrg, mMemberRequestBranchFrg);
        Bundle bundle = new Bundle();
        bundle.putSerializable("branch", branch);
        mInformationBranchFrg.setArguments(bundle);
        mMemberBranchFrg.setArguments(bundle);
        mMemberRequestBranchFrg.setArguments(bundle);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

}
