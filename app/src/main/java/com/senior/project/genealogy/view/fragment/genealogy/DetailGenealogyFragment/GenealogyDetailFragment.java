package com.senior.project.genealogy.view.fragment.genealogy.DetailGenealogyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment.UpdateGenealogyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GenealogyDetailFragment extends Fragment{

    @BindView(R.id.btnEdit)
    Button btnEdit;

    public GenealogyDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnEdit)
    public void onClick(){
        UpdateGenealogyFragment mFragment = new UpdateGenealogyFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.genealogy_container, mFragment)
                .addToBackStack(null)
                .commit();
    }

}
