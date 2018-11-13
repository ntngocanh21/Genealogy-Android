package com.senior.project.genealogy.view.fragment.profile.UpdateProfile;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileFragmentPresenterImpl;
import com.senior.project.genealogy.view.fragment.profile.ShowProfile.ProfileFragmentView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.senior.project.genealogy.util.Constants.EMPTY_STRING;

public class UpdateProfileFragment extends Fragment implements UpdateProfileFragmentView {

    @BindView(R.id.circle_profile)
    CircleImageView imgProfile;

    @BindView(R.id.edtFullname)
    EditText edtFullname;

    @BindView(R.id.edtBirthday)
    EditText edtBirthday;

    @BindView(R.id.edtMail)
    EditText edtMail;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.radioGender)
    RadioGroup radioGender;

    @BindView(R.id.radioMale)
    RadioButton radioMale;

    @BindView(R.id.radioFemale)
    RadioButton radioFemale;

    @BindView(R.id.btnDoneProfile)
    FloatingActionButton btnDoneProfile;

    private User user;
    private UpdateProfileFragmentPresenterImpl updateProfileFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;

    public UpdateProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_update, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.SHARED_PREFERENCES_KEY.TOKEN, EMPTY_STRING);
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_update_profile));
        }

        user = (User) getArguments().getSerializable("user");
        showProfile(user);

        return view;
    }

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @OnClick({R.id.btnDoneProfile, R.id.edtBirthday})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDoneProfile:
                updateProfileFragmentPresenterImpl = new UpdateProfileFragmentPresenterImpl(this);
                user.setFullname(edtFullname.getText().toString());
                user.setAddress(edtAddress.getText().toString());
                user.setBirthday(edtBirthday.getText().toString());
                if(radioMale.isChecked()){
                    user.setGender(true);
                } else {
                    user.setGender(false);
                }
                user.setMail(edtMail.getText().toString());
                updateProfileFragmentPresenterImpl.updateProfile(token, user);
                break;
            case R.id.edtBirthday:
                selectDate(edtBirthday);
                break;
        }
    }

    private Calendar mCalendar;

    @android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
    private void selectDate(final EditText edt){
        mCalendar = Calendar.getInstance();
        int day = mCalendar.get(Calendar.DATE);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt.setText(simpleDateFormat.format(mCalendar.getTime()));
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
    public void closeFragment(User user) {
        mUpdateProfileInterface.sendDataUpdateToProfile(user);
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_profile));
        }
        getActivity().onBackPressed();
    }

    @Override
    public void showProfile(User user) {
        if(user.isGender()){
            radioMale.setChecked(true);
        } else {
            radioFemale.setChecked(true);
        }

        edtFullname.setText(user.getFullname());
        edtBirthday.setText(user.getBirthday());
        edtMail.setText(user.getMail());
        edtAddress.setText(user.getAddress());
        edtBirthday.setText(user.getBirthday());
    }

    public interface UpdateProfileInterface{
        void sendDataUpdateToProfile(User user);
    }

    public UpdateProfileInterface mUpdateProfileInterface;

    public void attachInterface(UpdateProfileInterface updateProfileInterface){
        mUpdateProfileInterface = updateProfileInterface;
    }
}
