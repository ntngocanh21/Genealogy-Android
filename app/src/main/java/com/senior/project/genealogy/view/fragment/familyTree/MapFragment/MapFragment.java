package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.otaliastudios.zoom.ZoomLayout;
import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Edge;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;
import de.hdodenhof.circleimageview.CircleImageView;


public class MapFragment extends Fragment implements MapFragmentView{

    @BindView(R.id.graph)
    GraphView graphView;

    @BindView(R.id.zoomMap)
    ZoomLayout zoomMap;


    private MapFragmentPresenterImpl mapFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private Graph graph;

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
        initMap();
        mapFragmentPresenterImpl = new MapFragmentPresenterImpl(this);
        mapFragmentPresenterImpl.getFamilyTreeByBranchId(branchId, token);
        return view;
    }

    public void initMap(){
        graph = new Graph();
        Node node1 = new Node(new People("", null, null, ""));
        Node node2 = new Node(new People("", null, null, ""));
        graph.addEdge(node1, node2);
        graph.removeNode(node2);
        setAlgorithm(setGraphView(graph));
    }
    @Override
    public void showMap(List<People> peopleList){
        if(peopleList.size() == 0){
            showToast("Don't have any node");
        }else {
            if (peopleList.size() == 1){
                List<Node> nodeList = graph.getNodes();
                for (Node node : nodeList){
                    graph.removeNode(node);
                }
                Node node1 = new Node(peopleList.get(0));
                Node node2 = new Node(new People());
                graph.addEdge(node1, node2);
                graph.removeNode(node2);
            }
            else {
                List<Node> nodeListOld = graph.getNodes();
                for (Node node : nodeListOld){
                    graph.removeNode(node);
                }

                ArrayList<Node> nodeList = new ArrayList<>();
                for (People people : peopleList) {
                    nodeList.add(new Node(people));
                }

                for (Node node1 : nodeList) {
                    for (Node node2 : nodeList) {
                        if (((People) node2.getData()).getParentId() != null) {
                            if (((People) node2.getData()).getParentId() == ((People) node1.getData()).getId()) {
                                graph.addEdge(node1, node2);
                            }
                        }
                    }
                }
            }
        }
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
