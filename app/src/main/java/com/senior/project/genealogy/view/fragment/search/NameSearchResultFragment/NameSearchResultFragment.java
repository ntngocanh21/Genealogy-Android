package com.senior.project.genealogy.view.fragment.search.NameSearchResultFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment.BranchFragment;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;
import com.senior.project.genealogy.view.fragment.search.Adapter.SectionsPageAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NameSearchResultFragment extends Fragment implements NameSearchResultFragmentView{

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private ProgressDialog mProgressDialog;
    private GenealogyFragment mGenealogyFragment;
    private BranchFragment mBranchFragment;
    private List<Genealogy> genealogyList;
    private List<Branch> branchList;

    public NameSearchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_search_result, container, false);
        ButterKnife.bind(this, view);
        genealogyList = (List<Genealogy>) getArguments().getSerializable("genealogyList");
        branchList = (List<Branch>) getArguments().getSerializable("branchList");
        showGenealogyAndBranch(genealogyList, branchList);
        return view;

    }

    @Override
    public void showGenealogyAndBranch(List<Genealogy> genealogyList, List<Branch> branchList) {
        if (mGenealogyFragment == null)
            mGenealogyFragment = new GenealogyFragment();
        if (mBranchFragment == null)
            mBranchFragment = new BranchFragment();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TextView tv1 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
        tv1.setScaleY(-1);
        TextView tv2 = (TextView)(((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(1)).getChildAt(1));
        tv2.setScaleY(-1);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter.TitleStringUtils titleStringUtils = new SectionsPageAdapter.TitleStringUtils(getActivity());
        SectionsPageAdapter adapter;
        int numPage;
        List<Fragment> arrListFrg = new ArrayList<>();
        Bundle bundleGenealogy = new Bundle();
        bundleGenealogy.putSerializable("genealogyList", (Serializable) genealogyList);
        Bundle bundleBranch = new Bundle();
        bundleBranch.putSerializable("branchList", (Serializable) branchList);

        arrListFrg.add(mGenealogyFragment);
        arrListFrg.add(mGenealogyFragment);
        mGenealogyFragment.setArguments(bundleGenealogy);
        mBranchFragment.setArguments(bundleBranch);
        numPage = titleStringUtils.gettitlesNameSearch().length;
        adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager(), titleStringUtils.gettitlesNameSearch(), arrListFrg);
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(numPage);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public ProgressDialog initProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        return mProgressDialog;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = initProgressDialog();
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
