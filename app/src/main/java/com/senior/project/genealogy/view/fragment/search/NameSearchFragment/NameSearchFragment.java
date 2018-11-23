package com.senior.project.genealogy.view.fragment.search.NameSearchFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;


public class NameSearchFragment extends Fragment implements NameSearchFragmentView{

//    @BindView(R.id.viewpager)
//    ViewPager mViewPager;
//
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.rcvGenealogy)
    RecyclerView rcvGenealogy;

    private ProgressDialog mProgressDialog;
    private NameSearchFragmentPresenter nameSearchFragmentPresenter;
    private String token;

    public NameSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_search, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        GenealogyFragment mFragment = new GenealogyFragment();
        nameSearchFragmentPresenter = new NameSearchFragmentPresenterImpl(this);
        nameSearchFragmentPresenter.getGenealogies(token);

        return view;
    }

    @Override
    public void showGenealogy(List<Genealogy> genealogyList) {
        if(genealogyList != null){
            GenealogyFragment mFragment = new GenealogyFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("genealogyList", (Serializable) genealogyList);
            mFragment.setArguments(bundle);
            pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.result_frame);
        } else {
            //check
            showToast("null");
        }
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            if (getActivity() instanceof HomeActivity) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                if (type == HomeActivity.PushFrgType.REPLACE) {
                    ft.replace(mContainerId, fragment, tag);
                    ft.addToBackStack(fragment.getTag());
                    ft.commitAllowingStateLoss();
                } else if (type == HomeActivity.PushFrgType.ADD) {
                    ft.add(mContainerId, fragment, tag);
                    ft.addToBackStack(fragment.getTag());
                    ft.commit();
                }
                manager.executePendingTransactions();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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
