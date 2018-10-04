package com.senior.project.genealogy.view.fragment.genealogy.CreateGenealogyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.senior.project.genealogy.R;

public class CreateGenealogyFragment extends Fragment {

    public CreateGenealogyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_genealogy, container, false);
        return view;
    }
}
