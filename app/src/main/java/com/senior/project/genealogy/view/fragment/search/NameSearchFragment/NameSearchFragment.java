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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.Search;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment.GenealogyFragment;
import com.senior.project.genealogy.view.fragment.search.NameSearchResultFragment.NameSearchResultFragment;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;


public class NameSearchFragment extends Fragment implements NameSearchFragmentView{

    private ProgressDialog mProgressDialog;
    private NameSearchFragmentPresenter nameSearchFragmentPresenter;
    private String token;

    public NameSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_search, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        nameSearchFragmentPresenter = new NameSearchFragmentPresenterImpl(this);
        nameSearchFragmentPresenter.getGenealogies(token);

//        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    nameSearchFragmentPresenter.searchGenealogyByName(new Search(edtSearch.getText().toString()), token);
//                    return true;
//                }
//                return false;
//            }
//        });
        return view;
    }

    @Override
    public void showGenealogy(List<Genealogy> genealogyList) {
        if(genealogyList != null){
            GenealogyFragment mFragment = new GenealogyFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("genealogyList", (Serializable) genealogyList);
            mFragment.setArguments(bundle);
            pushFragment(HomeActivity.PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.result_frame);
        } else {
            //check
            showToast("null");
        }
    }

    @Override
    public void showGenealogyAndBranch(List<Genealogy> genealogyList, List<Branch> branchList) {
        NameSearchResultFragment mFragment = new NameSearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("genealogyList", (Serializable) genealogyList);
        bundle.putSerializable("branchList", (Serializable) branchList);
        mFragment.setArguments(bundle);
        pushFragment(HomeActivity.PushFrgType.REPLACE, mFragment, mFragment.getTag(), R.id.result_frame);
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
