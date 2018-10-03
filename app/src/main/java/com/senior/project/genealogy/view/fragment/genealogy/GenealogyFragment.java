package com.senior.project.genealogy.view.fragment.genealogy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.genealogy.adapter.RecyclerViewItemGenealogyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GenealogyFragment extends Fragment {

    @BindView(R.id.btnCreateGenealogy)
    Button btnCreateGenealogy;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    RecyclerViewItemGenealogyAdapter mRcvAdapter;
    List<Genealogy> data;

    public GenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy, container, false);
        ButterKnife.bind(this, view);

        data = new ArrayList<>();
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        data.add(new Genealogy(null,"Gia pha Ho Nguyen", "ls Gia pha Ho Nguyen"));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemGenealogyAdapter(getActivity(), fragmentManager, data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);

        return view;
    }

    @OnClick(R.id.btnCreateGenealogy)
    public void onClick(){
        CreateGenealogyFragment mFragment = new CreateGenealogyFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.genealogy_container, mFragment)
                .addToBackStack(null)
                .commit();
    }




}
