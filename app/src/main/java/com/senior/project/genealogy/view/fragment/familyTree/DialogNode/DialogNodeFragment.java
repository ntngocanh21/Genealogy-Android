package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.familyTree.MapFragment.MapFragmentPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogNodeFragment extends DialogFragment implements DialogNodeFragmentView{


    @BindView(R.id.edtBirthday)
    EditText edtBirthday;

    @BindView(R.id.tilBirthday)
    TextInputLayout tilBirthday;

    @BindView(R.id.relativeType)
    Spinner spRelative;

    private MapFragmentPresenterImpl mapFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;

    public DialogNodeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_node, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        showSpinnerRelative();
        //SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        //token = sharedPreferences.getString("token", "");
        //int branchId = getArguments().getInt("branchId");
        return view;
    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnNewNode, R.id.btnClose, R.id.edtBirthday})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.edtBirthday:
                selectDate();
                break;
        }
    }

    public void showSpinnerRelative() {
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getContext(), R.array.relative_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRelative.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spRelative.setAdapter(dataAdapter);
    }


    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    private void selectDate(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtBirthday.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
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

    public static DialogNodeFragment newInstance(String data) {
        DialogNodeFragment dialog = new DialogNodeFragment();
        Bundle args = new Bundle();
        args.putString("data", data);
        dialog.setArguments(args);
        return dialog;
    }

}
