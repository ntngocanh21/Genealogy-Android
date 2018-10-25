package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.otaliastudios.zoom.ZoomLayout;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.response.User;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.activity.register.RegisterActivity;
import com.senior.project.genealogy.view.fragment.familyTree.DialogNode.DialogNodeFragment;
import com.senior.project.genealogy.view.fragment.familyTree.DialogProfile.DialogProfileFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;
import de.hdodenhof.circleimageview.CircleImageView;


public class MapFragment extends Fragment implements MapFragmentView{

    @BindView(R.id.graph)
    GraphView graphView;

    @BindView(R.id.addNode)
    FloatingActionButton addNode;


    private MapFragmentPresenterImpl mapFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private Graph graph;
    protected BaseGraphAdapter<ViewHolder> adapter;

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
        mapFragmentPresenterImpl.getFamilyTreeByBranchId(branchId, token);
        return view;
    }

    @Override
    public void showMap(List<People> peopleList){

        if(peopleList.size() == 0){
            showToast("Don't have any node");
            addNode.setVisibility(View.VISIBLE);
        }else {
            graph = new Graph();
            if (peopleList.size() == 1){
                Node node = new Node(peopleList.get(0));
                graph.addNode(node);
            }
            else {
                ArrayList<Node> nodeList = new ArrayList<>();
                for (People people : peopleList) {
                    graph.addNode(new Node(people));
                    if (people.getParentId() != null) {
                        graph.addEdge(findNode(graph, people.getParentId()), findNode(graph, people.getId()));
                    }
                }
            }
            setupAdapter(graph);
        }
    }

    @Override
    public void deletePeople(int peopleId) {
        graph.removeNode(findNode(graph, peopleId));
        adapter.notifySizeChanged();
    }

    public Node findNode(Graph graph, int id){
        List<Node> nodeList = graph.getNodes();
        Node nodeReturn = null;
        for (Node node : nodeList){
            if(((People) node.getData()).getId() == id){
                nodeReturn = node;
            }
        }
        return nodeReturn;
    }

    private void setupAdapter(final Graph graph) {
        adapter = new BaseGraphAdapter<ViewHolder>(this.getContext(), R.layout.node, graph) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.txtName.setText(((People)data).getName());
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                if(((People)data).getBirthday() != null)
                {
                    final String date = formatter.format(((People)data).getBirthday());
                    viewHolder.txtDate.setText(date);
                } else {
                    viewHolder.txtDate.setText("");
                }

                if(((People)data).getGender() != null){
                    if(((People)data).getGender() == 1){
                        viewHolder.civProfile.setImageResource(R.drawable.man);
                    } else {
                        viewHolder.civProfile.setImageResource(R.drawable.woman);
                    }
                }
            }
        };
        setAlgorithm(adapter);
        graphView.setAdapter(adapter);
        graphView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                People people= ((People)graph.getNode(position).getData());
                DialogProfileFragment dialogProfileFragment = DialogProfileFragment.newInstance(people, getArguments().getInt("branchId"));
                dialogProfileFragment.show(getActivity().getSupportFragmentManager(), null);

                dialogProfileFragment.attackInterface(new DialogProfileFragment.DialogProfileInterface() {
                    @Override
                    public void sendDataToMap(People people) {
                        graph.addNode(new Node(people));
                        graph.addEdge(findNode(graph, people.getParentId()), findNode(graph, people.getId()));
                    }
                });

            }
        });
        graphView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showLoginAlertDialog(adapter.getNode(position));
                return false;
            }
        });
    }

    public void showLoginAlertDialog(final Node node){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete this person");
        builder.setMessage("Are you sure you want to delete " + ((People) node.getData()).getName() + " from the family tree?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mapFragmentPresenterImpl.deletePeople(((People) node.getData()).getId(), token);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setAlgorithm(GraphAdapter adapter) {
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(400)
                .setLevelSeparation(300)
                .setSubtreeSeparation(500)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
    }

    @OnClick(R.id.addNode)
    public void onClick()
    {
        //when map don't have any node!!!
        DialogNodeFragment dialogNodeFragment = DialogNodeFragment.newInstance(null, String.valueOf(getArguments().getInt("branchId")));
        dialogNodeFragment.show(getActivity().getSupportFragmentManager(), null);
        dialogNodeFragment.attackInterface(new DialogNodeFragment.CreateNodeInterface() {
            @Override
            public void sendDataToMap(People people) {
                graph = new Graph();
                graph.addNode(new Node(people));
                setupAdapter(graph);
                addNode.setVisibility(View.GONE);
            }
        });
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

    private class ViewHolder {
        TextView txtName;
        TextView txtDate;
        CircleImageView civProfile;
        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtName);
            txtDate = view.findViewById(R.id.txtDate);
            civProfile = view.findViewById(R.id.civProfile);
        }
    }
}
