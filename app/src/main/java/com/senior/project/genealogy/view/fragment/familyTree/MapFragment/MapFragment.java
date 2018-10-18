package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;


public class MapFragment extends Fragment implements MapFragmentView{

    @BindView(R.id.graph)
    GraphView graphView;

    @BindView(R.id.btnAddPeople)
    FloatingActionButton btnAddPeople;

    private MapFragmentPresenterImpl mapFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private int nodeCount = 1;

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ((HomeActivity) getActivity()).updateTitleBar(getString(R.string.frg_family_tree));
        int branchId = getArguments().getInt("branchId");
        mapFragmentPresenterImpl = new MapFragmentPresenterImpl(this);
//        mapFragmentPresenterImpl.getFamilyTreeByBranchId(branchId, token);
        showMap();
        return view;
    }

//    public void showMap(List<People> peopleList){
    public void showMap(){
        final Graph graph = new Graph();
        final Node node1 = new Node(getNodeText());
        final Node node2 = new Node(getNodeText());
        final Node node3 = new Node(getNodeText());
        final Node node4 = new Node(getNodeText());
        final Node node5 = new Node(getNodeText());

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node1, node4);
        graph.addEdge(node2, node5);
        setAlgorithm(setGraphView(graph));
    }

    public BaseGraphAdapter<ViewHolder> setGraphView(Graph graph){
        final BaseGraphAdapter<ViewHolder> adapter = new BaseGraphAdapter<ViewHolder>(this.getContext(), R.layout.node, graph) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.txtName.setText(data.toString());
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                final String date = formatter.format(new Date());
                viewHolder.txtDate.setText(date);
            }
        };
        graphView.setAdapter(adapter);
        return adapter;
    }

    public void setAlgorithm(BaseGraphAdapter<ViewHolder> adapter){
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
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

    private String getNodeText() {
        return "Node " + nodeCount++;
    }

    private class ViewHolder {
        TextView txtName;
        TextView txtDate;

        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtName);
            txtDate = view.findViewById(R.id.txtDate);
        }
    }
}
