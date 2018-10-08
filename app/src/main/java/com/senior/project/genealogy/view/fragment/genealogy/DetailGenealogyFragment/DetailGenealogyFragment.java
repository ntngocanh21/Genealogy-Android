package com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment.CreateGenealogyFragment;
import com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment.UpdateGenealogyFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailGenealogyFragment extends Fragment implements DetailGenealogyFragmentView{

    @BindView(R.id.txtGenealogyName)
    TextView txtGenealogyName;

    @BindView(R.id.txtGenealogyOwner)
    TextView txtGenealogyOwner;

    @BindView(R.id.txtGenealogyDate)
    TextView txtGenealogyDate;

    @BindView(R.id.txtBranches)
    TextView txtBranches;

    @BindView(R.id.txtHistory)
    TextView txtHistory;

    @BindView(R.id.btnEditGenealogy)
    FloatingActionButton btnEditGenealogy;

    private DetailGenealogyFragmentPresenterImpl detailGenealogyFragmentPresenter;
    private ProgressDialog mProgressDialog;
    private Genealogy genealogy;

    public DetailGenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_detail, container, false);
        ButterKnife.bind(this, view);

        genealogy = (Genealogy) getArguments().getSerializable("genealogy");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        showGenealogy(genealogy);
        return view;
    }

    /**
     * Interface communicate DetailFragment vs HomeActivity
     * Back -> Manager (DetailFragment)
     * Manager.getCould > 0 => pop
     *
     * interface x => x.isNestedFragment() [Detail]
     * => Interface(HomeActivity) => Detail
     */
    public interface DetailGenealogyInterface {
        boolean isNestedFrragment();
    }

    private DetailGenealogyInterface mDetailGenealogyInterface;


    @OnClick(R.id.btnEditGenealogy)
    public void onClick() {
        UpdateGenealogyFragment mFragment = new UpdateGenealogyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("genealogy", genealogy);
        mFragment.setArguments(bundle);
        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.genealogy_detail_fram);
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
    public void showGenealogy(Genealogy genealogy) {
        txtGenealogyName.setText(genealogy.getName());
        txtHistory.setText(genealogy.getHistory());
        txtBranches.setText(genealogy.getBranch().toString());
        txtGenealogyOwner.setText(genealogy.getOwner());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String genealogyDate = formatter.format(genealogy.getDate());
        txtGenealogyDate.setText(genealogyDate);
    }

    @Override
    public void closeFragment() {
    }
}
