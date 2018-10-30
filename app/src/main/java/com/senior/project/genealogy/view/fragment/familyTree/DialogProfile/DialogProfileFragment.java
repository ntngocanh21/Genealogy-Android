package com.senior.project.genealogy.view.fragment.familyTree.DialogProfile;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.view.fragment.familyTree.DialogNode.DialogNodeFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.blox.graphview.Graph;
import de.blox.graphview.Node;

public class DialogProfileFragment extends DialogFragment implements DialogProfileFragmentView{

    @BindView(R.id.btnMenu)
    FloatingActionMenu btnMenu;

    @BindView(R.id.btnAddNode)
    FloatingActionButton btnAddNode;

    @BindView(R.id.btnRelative)
    FloatingActionButton btnRelative;

    @BindView(R.id.tvProfile)
    TextView tvProfile;

    @BindView(R.id.imgGenger)
    ImageView imgGenger;

    @BindView(R.id.txtFullname)
    TextView txtFullname;

    @BindView(R.id.txtNickname)
    TextView txtNickname;

    @BindView(R.id.txtBirthday)
    TextView txtBirthday;

    @BindView(R.id.txtDeathdayTitle)
    TextView txtDeathdayTitle;

    @BindView(R.id.txtDeathday)
    TextView txtDeathday;

    @BindView(R.id.txtAddress)
    TextView txtAddress;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    private People people;
    public DialogProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_profile, container, false);
        setCancelable(false);
        ButterKnife.bind(this, view);
        people = (People) getArguments().getSerializable("people");
        showInformation(people);
        return view;
    }

    public static DialogProfileFragment newInstance(People people, int branchId) {
        DialogProfileFragment dialog = new DialogProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("people", people);
        bundle.putInt("branchId", branchId);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void showInformation(People people){
        tvProfile.setText("Information of " + people.getName());
        if(people.getGender() == 1){
            imgGenger.setImageResource(R.drawable.ic_male);
        } else {
            imgGenger.setImageResource(R.drawable.ic_female);
        }
        txtFullname.setText(people.getName());
        txtNickname.setText(people.getNickname());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (people.getBirthday() != null){
            String birthday = formatter.format(people.getBirthday());
            txtBirthday.setText(birthday);
        }
        if (people.getDeathDay() != null){
            txtDeathdayTitle.setVisibility(View.VISIBLE);
            txtDeathday.setVisibility(View.VISIBLE);
            String deathday = formatter.format(people.getDeathDay());
            txtDeathday.setText(deathday);
        }
        txtAddress.setText(people.getAddress());
        txtDescription.setText(people.getDescription());
    }

    @OnClick({R.id.btnAddNode, R.id.btnRelative, R.id.btnMenu, R.id.btnClose})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                this.dismiss();
                break;
            case R.id.btnAddNode:
                DialogNodeFragment dialogNodeFragment = DialogNodeFragment.newInstance(people, null);
                dialogNodeFragment.show(getActivity().getSupportFragmentManager(), null);
                dialogNodeFragment.attackInterface(new DialogNodeFragment.CreateNodeInterface() {
                    @Override
                    public void sendDataToMap(People people) {
                        mDialogProfileInterface.sendDataToMap(people);
                        getDialog().dismiss();
                    }
                });
                break;
            case R.id.btnRelative:
                break;
        }
    }

    public interface DialogProfileInterface{
        void sendDataToMap(People people);
    }

    public DialogProfileInterface mDialogProfileInterface;

    public void attackInterface(DialogProfileInterface dialogProfileInterface){
        mDialogProfileInterface = dialogProfileInterface;
    }

}
