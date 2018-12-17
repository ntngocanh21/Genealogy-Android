package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogEventFragment extends DialogFragment implements DialogEventFragmentView {

    @BindView(R.id.tvProfile)
    TextView tvProfile;

    @BindView(R.id.edtEventName)
    EditText edtEventName;

    @BindView(R.id.edtSetTime)
    EditText edtSetTime;

    @BindView(R.id.edtContent)
    EditText edtContent;

    private Calendar mCalendar;

    public DialogEventFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_event, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        initComponents();
        return view;
    }

    private void initComponents() {
        tvProfile.setText(getResources().getString(R.string.frg_title_event));
        resetFrom();
    }

    private void resetFrom() {
        edtEventName.setError(null);
        edtContent.setError(null);
        edtSetTime.setError(null);
    }

    public static DialogEventFragment newInstance() {
        DialogEventFragment dialog = new DialogEventFragment();
        return dialog;
    }

    @OnClick({ R.id.btnClose, R.id.btnCreateEvent, R.id.edtSetTime })
    void onClick(View view) {
        if (Utils.isDoubleClick()) return;
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.btnCreateEvent:
                if(isDataValid()) {
                    mDialogEventInterface.sendDataToFrg(getDataFromForm());
                }
                break;
            case R.id.edtSetTime:
                selectDate(edtSetTime);
                break;
        }
    }

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

    public Event getDataFromForm() {
        Event event = new Event();
        event.setTitle(edtEventName.getText().toString());
        event.setContent(edtContent.getText().toString());
        event.setDate(edtSetTime.getText().toString());
        return event;
    }

    public boolean isDataValid() {
        if (edtEventName.getText().toString().isEmpty()) {
            edtEventName.setError(getString(R.string.error_event_name));
            edtEventName.requestFocus();
            return false;
        } else if (edtSetTime.getText().toString().isEmpty()) {
            edtSetTime.setError(getString(R.string.error_set_time));
            edtSetTime.requestFocus();
            return false;
        } else if (edtContent.getText().toString().isEmpty()) {
            edtContent.setError(getString(R.string.error_event_content));
            edtContent.requestFocus();
            return false;
        }
        return true;
    }

    public interface DialogEventInterface {
        void sendDataToFrg(Event event);
    }

    public DialogEventInterface mDialogEventInterface;

    public void attackInterface(DialogEventInterface dialogEventInterface){
        mDialogEventInterface = dialogEventInterface;
    }
}
