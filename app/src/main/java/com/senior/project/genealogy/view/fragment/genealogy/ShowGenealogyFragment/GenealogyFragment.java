package com.senior.project.genealogy.view.fragment.genealogy.ShowGenealogyFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.CreateGenealogyFragment;
import com.senior.project.genealogy.view.fragment.genealogy.adapter.RecyclerViewItemGenealogyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * CreateGenealogyFragment => click button [ADD]
 * DetailGenealogyFragment => click each row
 */
public class GenealogyFragment extends Fragment implements GenealogyFragmentView {

    @BindView(R.id.btnCreateGenealogy)
    FloatingActionButton btnCreateGenealogy;

    @BindView(R.id.recycler_view)

    RecyclerView mRecyclerView;

    RecyclerViewItemGenealogyAdapter mRcvAdapter;
    List<Genealogy> data;

    private GenealogyFragmentPresenterImpl genealogyFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;

    public GenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        genealogyFragmentPresenterImpl = new GenealogyFragmentPresenterImpl(this);
        genealogyFragmentPresenterImpl.getGenealogiesByUsername(token);

        /**
         * After onCreate Fragment. We will attach interface to get event outside fragment and handle inside it.
         * For example: Here, we will handle onBackPress()
         */
        if (getActivity() instanceof HomeActivity)
            ((HomeActivity) getActivity()).attachFragInterface(new HomeActivity.HomeInterface() {
                @Override
                public boolean isExistedNestedFrag() {
                    if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                        /**
                         * Pop GenealogyFragment
                         * - CreateGenealogyFragment
                         * - DetailGenealogyFragment
                         * No Handle
                         * - DetailGenealogyFragment
                         * - UpdateGenealogyFragment
                         * => Pop
                         * Check DetailFragmentGenealogy has child fragments => pop
                         * pop -> POP -> Back
                         */
                        if (getActivity() instanceof HomeActivity) {
                            String currentTitle = ((HomeActivity)getActivity()).getCurrentTitleBar();
                            if (currentTitle.equals("Genealogy information")){
                                /**
                                 * This Detail Fragment
                                 * You should check nested fragment and then pop it
                                 */

                            }
                        }
                        getChildFragmentManager().popBackStack();
                        ((HomeActivity) getActivity()).updateTitleBar("My genealogies");
                        return true;
                    } else {
                        return false;
                    }
                }
            });

        return view;
    }

    @OnClick(R.id.btnCreateGenealogy)
    public void onClick() {
        CreateGenealogyFragment mFragment = new CreateGenealogyFragment();
        mFragment.attackInterface(new CreateGenealogyFragment.CreateGenealogyInterface() {
            @Override
            public void sendDataToListGenealogy(Genealogy genealogy) {
                mRcvAdapter.updateGenealogy(genealogy);
            }
        });
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar("Create new genealogy");
        }
        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.genealogy_frame);
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentManager manager = getChildFragmentManager();
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

    @Override
    public void showGenealogy(List<Genealogy> genealogyList) {
        data = new ArrayList<>();
        for (Genealogy genealogy : genealogyList) {
            data.add(genealogy);
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        mRcvAdapter = new RecyclerViewItemGenealogyAdapter(getActivity(), fragmentManager, data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);
    }
}
