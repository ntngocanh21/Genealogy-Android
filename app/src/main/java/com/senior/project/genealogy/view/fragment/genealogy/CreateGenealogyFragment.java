package com.senior.project.genealogy.view.fragment.genealogy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senior.project.genealogy.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateGenealogyFragment extends Fragment {

    @BindView(R.id.btnCreate)
    Button btnCreate;

    public CreateGenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_genealogy, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
