package com.senior.project.genealogy.view.fragment.genealogy.UpdateGenealogyFragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateGenealogyFragment extends Fragment implements UpdateGenealogyFragmentView{

    @BindView(R.id.edtGenealogyName)
    EditText edtGenealogyName;

    @BindView(R.id.edtGenealogyHistory)
    EditText edtGenealogyHistory;

    @BindView(R.id.txtGenealogyOwner)
    TextView txtGenealogyOwner;

    @BindView(R.id.txtGenealogyDate)
    TextView txtGenealogyDate;

    @BindView(R.id.txtBranches)
    TextView txtBranches;

    @BindView(R.id.btnDoneEdit)
    FloatingActionButton btnDoneEdit;

    public UpdateGenealogyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genealogy_update, container, false);
        ButterKnife.bind(this, view);
        Genealogy genealogy = (Genealogy) getArguments().getSerializable("genealogy");
        showGenealogy(genealogy);
        return view;
    }

    @Override
    public void closeFragment() {
        getActivity().onBackPressed();
    }

    @Override
    public void showGenealogy(Genealogy genealogy) {
        edtGenealogyName.setText(genealogy.getName());
        edtGenealogyHistory.setText(genealogy.getHistory());
        txtGenealogyOwner.setText(genealogy.getOwner());
        txtBranches.setText(genealogy.getBranch().toString());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String genealogyDate = formatter.format(genealogy.getDate());
        txtGenealogyDate.setText(genealogyDate);
    }
}
