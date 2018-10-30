package com.senior.project.genealogy.view.fragment.branch.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.fragment.branch.DetailInformationBranchFragment.DetailInformationBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.DetailMemberBranchFragment.DetailMemberBranchFragment;

import javax.inject.Inject;

public class SectionsPageAdapter extends FragmentStatePagerAdapter {

    private DetailInformationBranchFragment mInformationBranchFrg;
    private DetailMemberBranchFragment mMemberBranchFrg;
    private TitleStringUtils titles;

    public SectionsPageAdapter(FragmentManager fragmentManager, TitleStringUtils titleStringUtils, DetailInformationBranchFragment informationBranchFrg, DetailMemberBranchFragment memberBranchFrg) {
        super(fragmentManager);
        titles = titleStringUtils;
        mInformationBranchFrg = informationBranchFrg;
        mMemberBranchFrg = memberBranchFrg;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:{
                return mInformationBranchFrg;
            }
            case 1:{
                return mMemberBranchFrg;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.getGroupTitleFragment().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.getGroupTitleFragment()[position];
    }

    public static class TitleStringUtils {

        private Context mContext;

        @Inject
        public TitleStringUtils(Context context) {
            mContext = context;
        }

        public String[] getGroupTitleFragment() {
            return new String[]{mContext.getResources().getString(R.string.tab_title_information), mContext.getString(R.string.tab_title_member)};
        }
    }
}
