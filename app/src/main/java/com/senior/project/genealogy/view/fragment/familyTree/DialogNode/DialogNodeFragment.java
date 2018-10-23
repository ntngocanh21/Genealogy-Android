package com.senior.project.genealogy.view.fragment.familyTree.DialogNode;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
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

    @BindView(R.id.tvNewNode)
    TextView txtNewNode;

    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtNickname)
    EditText edtNickname;

    @BindView(R.id.edtDescription)
    EditText edtDescription;

    private DialogNodeFragmentPresenterImpl dialogNodeFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private People people;

    public DialogNodeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_node, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        showSpinnerRelative();
        people = (People) getArguments().getSerializable("people");
        txtNewNode.setText("Add relative to " + people.getName());
        return view;
    }

    public static DialogNodeFragment newInstance(People people) {
        DialogNodeFragment dialog = new DialogNodeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("people", people);
        dialog.setArguments(bundle);
        return dialog;
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
            case R.id.btnNewNode:
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                People newPeople = new People();
                newPeople.setBranchId(people.getBranchId());
                newPeople.setName(edtFullName.getText().toString());
                newPeople.setNickname(edtNickname.getText().toString());
//                newPeople.setBirthday();

                newPeople.setAddress(edtAddress.getText().toString());
//                newPeople.setDeathDay();
//                newPeople.setImage();
//                newPeople.setDegree();
                newPeople.setDescription(edtDescription.getText().toString());
//                newPeople.setLifeIndex();
//                newPeople.setParentId();
//                newPeople.setGender();
                int peopleId = getArguments().getInt("peopleId");
                int parentId = getArguments().getInt("parentId");
//                dialogNodeFragmentPresenterImpl = new DialogNodeFragmentPresenterImpl(this);
//                dialogNodeFragmentPresenterImpl.createPeople(people ,token);
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

    @Override
    public void closeDialogFragment(List<People> peopleList) {
        mCreateNodeInterface.sendDataToMap(peopleList.get(0));
        this.dismiss();
        getActivity().onBackPressed();
    }

    public interface CreateNodeInterface{
        void sendDataToMap(People people);
    }

    public CreateNodeInterface mCreateNodeInterface;

    public void attackInterface(CreateNodeInterface createNodeInterface){
        mCreateNodeInterface = createNodeInterface;
    }

}
