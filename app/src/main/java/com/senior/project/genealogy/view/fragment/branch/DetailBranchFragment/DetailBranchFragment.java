package com.senior.project.genealogy.view.fragment.branch.DetailBranchFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
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

        if (branch.getRole()== Constants.ROLE.ADMIN_ROLE || branch.getRole() == Constants.ROLE.MOD_ROLE){
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
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
            tv1.setScaleY(-1);
            TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
            tv2.setScaleY(-1);
            TextView tv3 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(2)).getChildAt(1));
            tv3.setScaleY(-1);
        } else {
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
            tv1.setScaleY(-1);
            TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
            tv2.setScaleY(-1);
        }
        return view;
    }

    public void updateMember(User user){
        mMemberBranchFrg.updateMember(user);
    }

//    private void setupTabIcons() {
//        mTabLayout.getTabAt(0).setIcon(Integer.valueOf(R.drawable.ic_infor));
//        mTabLayout.getTabAt(1).setIcon(Integer.valueOf(R.drawable.ic_member));
//        mTabLayout.getTabAt(2).setIcon(Integer.valueOf(R.drawable.ic_request));
//    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter.TitleStringUtils titleStringUtils = new SectionsPageAdapter.TitleStringUtils(getActivity());
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putSerializable("branch", branch);
        if (branch.getRole()== Constants.ROLE.ADMIN_ROLE || branch.getRole() == Constants.ROLE.MOD_ROLE){
            adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), titleStringUtils, mInformationBranchFrg, mMemberBranchFrg, mMemberRequestBranchFrg);
            mInformationBranchFrg.setArguments(bundle);
            mMemberBranchFrg.setArguments(bundle);
            mMemberRequestBranchFrg.setArguments(bundle);
        } else {
            adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), titleStringUtils, mInformationBranchFrg, mMemberBranchFrg);
            mInformationBranchFrg.setArguments(bundle);
            mMemberBranchFrg.setArguments(bundle);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

}
