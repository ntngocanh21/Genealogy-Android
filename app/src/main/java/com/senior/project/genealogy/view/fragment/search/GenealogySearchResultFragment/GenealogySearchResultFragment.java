package com.senior.project.genealogy.view.fragment.search.GenealogySearchResultFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.search.Adapter.RecyclerViewItemGenealogyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GenealogySearchResultFragment extends Fragment implements GenealogySearchResultFragmentView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.txtNotice)
    TextView txtNotice;

    RecyclerViewItemGenealogyAdapter mRcvAdapter;
    List<Genealogy> genealogies;

    private ProgressDialog mProgressDialog;
    private String token;
    private List<Genealogy> genealogyList;

    public GenealogySearchResultFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_search_result, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            genealogyList = (List<Genealogy>) getArguments().getSerializable("genealogyList");
            showGenealogy(genealogyList);
        }
        return view;
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
        genealogies = new ArrayList<>();

        if(genealogyList == null){
            txtNotice.setVisibility(View.VISIBLE);
        }
        else {
            genealogies.addAll(genealogyList);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        mRcvAdapter = new RecyclerViewItemGenealogyAdapter(getActivity(), fragmentManager, genealogies);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);
    }
}
