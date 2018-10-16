package com.senior.project.genealogy.view.fragment.branch.ShowBranchFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.branch.CreateBranchFragment.CreateBranchFragment;
import com.senior.project.genealogy.view.fragment.branch.adapter.RecyclerItemBranchTouchHelper;
import com.senior.project.genealogy.view.fragment.branch.adapter.RecyclerViewItemBranchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BranchFragment extends Fragment implements BranchFragmentView, RecyclerItemBranchTouchHelper.RecyclerItemTouchHelperListener{

    @BindView(R.id.btnCreateBranch)
    FloatingActionButton btnCreateBranch;

    @BindView(R.id.recyclerViewBranch)
    RecyclerView recyclerViewBranch;

    @BindView(R.id.spGenealogy)
    Spinner spGenealogy;

    RecyclerViewItemBranchAdapter mRcvAdapter;
    List<Branch> branches;

    private BranchFragmentPresenterImpl branchFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;

    public BranchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch, container, false);
        ButterKnife.bind(this, view);

        //getArguments()

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ((HomeActivity) getActivity()).updateTitleBar("Branches");
        branchFragmentPresenterImpl = new BranchFragmentPresenterImpl(this);
        branchFragmentPresenterImpl.getGenealogiesByUsername(token);

        return view;
    }

    public void addItemsOnSpinnerGenealogy(List<Genealogy> genealogyList) {
        ArrayAdapter<Genealogy> dataAdapter = new ArrayAdapter<Genealogy>(getContext(), android.R.layout.simple_spinner_item, genealogyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenealogy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Genealogy genealogy = (Genealogy) spGenealogy.getSelectedItem();
                showBranch(genealogy.getBranchList());
                //branchFragmentPresenterImpl.getBranchesByGenealogyId(token, genealogy.getId());
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spGenealogy.setAdapter(dataAdapter);
    }

    @OnClick(R.id.btnCreateBranch)
    public void onClick() {
        CreateBranchFragment mFragment = new CreateBranchFragment();
        Genealogy genealogy = (Genealogy) spGenealogy.getSelectedItem();
        Bundle bundle = new Bundle();
        bundle.putInt("genealogyId", genealogy.getId());
        bundle.putString("genealogyName", genealogy.getName());
        mFragment.setArguments(bundle);

        mFragment.attackInterface(new CreateBranchFragment.CreateBranchInterface() {
            @Override
            public void sendDataToListBranch(Branch branch) {
                mRcvAdapter.updateBranch(branch);
            }
        });
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).updateTitleBar("Create new branch");
        }
        pushFragment(HomeActivity.PushFrgType.ADD, mFragment, mFragment.getTag(), R.id.branch_frame);
    }

    public void pushFragment(HomeActivity.PushFrgType type, Fragment fragment, String tag, @IdRes int mContainerId) {
        try {
            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            if (type == HomeActivity.PushFrgType.REPLACE) {
                ft.replace(mContainerId, fragment, tag);
                ft.addToBackStack(fragment.getTag());
                ft.commitAllowingStateLoss();
            } else if (type == HomeActivity.PushFrgType.ADD) {
                ft.add(mContainerId, fragment, tag);
                ft.addToBackStack(fragment.getTag());
                ft.commit();
            }
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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
    public void showBranch(List<Branch> branchList) {
        branches = new ArrayList<>();
        for (Branch branch : branchList) {
            branches.add(branch);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mRcvAdapter = new RecyclerViewItemBranchAdapter(getActivity(), fragmentManager, branches);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewBranch.setLayoutManager(layoutManager);
        recyclerViewBranch.setAdapter(mRcvAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemBranchTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewBranch);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerViewItemBranchAdapter.RecyclerViewHolder) {
            final int deletedIndex = viewHolder.getAdapterPosition();
            showAlertDialog("Delete", "Are you sure?", "Delete", "Cancel", viewHolder);
        }
    }

    public void showAlertDialog(String title, String message, String positive, String negative, final RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int branchId = branches.get(viewHolder.getAdapterPosition()).getId();
                branchFragmentPresenterImpl.deleteBranch(branchId, token, viewHolder);
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mRcvAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void deleteItemBranch(RecyclerView.ViewHolder viewHolder) {
        mRcvAdapter.removeItem(viewHolder.getAdapterPosition());
    }
}
