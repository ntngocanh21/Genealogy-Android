package com.senior.project.genealogy.view.fragment.familyTree.MapFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Branch;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.response.UserBranchPermission;
import com.senior.project.genealogy.util.Constants;
import com.senior.project.genealogy.view.activity.home.HomeActivity;
import com.senior.project.genealogy.view.fragment.familyTree.DialogNode.DialogNodeFragment;
import com.senior.project.genealogy.view.fragment.familyTree.DialogProfile.DialogProfileFragment;

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

    @BindView(R.id.btnFollow)
    ImageButton btnFollow;

    @BindView(R.id.btnFollowed)
    ImageButton btnFollowed;

    @BindView(R.id.btnWaiting)
    ImageButton btnWaiting;

    @BindView(R.id.txtNotice)
    TextView txtNotice;

    @BindView(R.id.txtNoticeMemberAndGuest)
    TextView txtNoticeMemberAndGuest;

    private MapFragmentPresenterImpl mapFragmentPresenterImpl;
    private ProgressDialog mProgressDialog;
    private String token;
    private Graph graph;
    private Branch branch;
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
        branch = (Branch) getArguments().getSerializable("branch");
        if (branch.getRole() == Constants.ROLE.ADMIN_ROLE || branch.getRole() == Constants.ROLE.MOD_ROLE
                || branch.getRole() == Constants.ROLE.MEMBER_ROLE){
            btnFollowed.setVisibility(View.VISIBLE);
        }else {
            btnFollow.setVisibility(View.VISIBLE);
        }
        mapFragmentPresenterImpl = new MapFragmentPresenterImpl(this);
        mapFragmentPresenterImpl.getFamilyTreeByBranchId(branch.getId(), token);
        return view;
    }

    @Override
    public void showMap(List<People> peopleList){

        if(peopleList.size() == 0){
            if (branch.getRole() == Constants.ROLE.ADMIN_ROLE || branch.getRole() == Constants.ROLE.MOD_ROLE){
                addNode.setVisibility(View.VISIBLE);
                txtNotice.setVisibility(View.VISIBLE);
            }else {
                addNode.setVisibility(View.GONE);
                txtNoticeMemberAndGuest.setVisibility(View.VISIBLE);
            }
        }else {
            txtNotice.setVisibility(View.GONE);
            graph = new Graph();
            if (peopleList.size() == 1){
                Node node = new Node(peopleList.get(0));
                graph.addNode(node);
            }
            else {
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
        if (graph.getNodes().size() == 0){
            txtNotice.setVisibility(View.VISIBLE);
            addNode.setVisibility(View.VISIBLE);
        }
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
//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                if(((People)data).getBirthday() != null)
                {
//                    final String date = formatter.format(((People)data).getBirthday());
                    final String date = ((People)data).getBirthday();
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

                if(((People)data).getAppellation() != null){
                    viewHolder.txtRelation.setVisibility(View.VISIBLE);
                    viewHolder.txtRelation.setText(((People)data).getAppellation());

                    if("Center".equals(((People)data).getAppellation())) {
                        viewHolder.txtRelation.setText("Tôi");
                        viewHolder.txtRelation.setTypeface(null, Typeface.ITALIC);
                        viewHolder.txtRelation.setTypeface(null, Typeface.BOLD);
                        @ColorInt int nodeBgColor = 0xFFE9C2B3;
                        viewHolder.nodeBg.setBackgroundColor(nodeBgColor);
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

                dialogProfileFragment.attackInterface(new DialogProfileFragment.GetRelationInterface() {
                    @Override
                    public void sendDataToMap(List<People> peopleList) {
                        deleteNode();

                        for (People people : peopleList) {
                            graph.addNode(new Node(people));
                            if (people.getParentId() != null) {
                                graph.addEdge(findNode(graph, people.getParentId()), findNode(graph, people.getId()));
                            }
                        }
                    }
                });

            }
        });
        graphView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (branch.getRole() == Constants.ROLE.ADMIN_ROLE || branch.getRole() == Constants.ROLE.MOD_ROLE){
                    showDeleteAlertDialog(adapter.getNode(position));
                }
                return false;
            }
        });
    }

    public void showDeleteAlertDialog(final Node node){
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

    private void deleteNode(){
        List<Node> nodes = graph.getNodes();
        if(nodes.size() != 0){
            for (Node node : nodes){
                graph.removeNode(node);
                break;
            }
            deleteNode();
        }
    }

    public void setAlgorithm(GraphAdapter adapter) {
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(400)
                .setLevelSeparation(200)
                .setSubtreeSeparation(500)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
    }

    @OnClick({R.id.addNode, R.id.btnFollow, R.id.btnFollowed, R.id.btnWaiting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNode:
                DialogNodeFragment dialogNodeFragment = DialogNodeFragment.newInstance(null, String.valueOf(getArguments().getInt("branchId")));
                dialogNodeFragment.show(getActivity().getSupportFragmentManager(), null);
                dialogNodeFragment.attackInterface(new DialogNodeFragment.CreateNodeInterface() {
                    @Override
                    public void sendDataToMap(People people) {
                        graph = new Graph();
                        graph.addNode(new Node(people));
                        setupAdapter(graph);
                        addNode.setVisibility(View.GONE);
                        txtNotice.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.btnFollow:
                UserBranchPermission userBranchPermission = new UserBranchPermission(branch.getId(), Constants.ROLE.MEMBER_ROLE);
                mapFragmentPresenterImpl.joinBranch(userBranchPermission, token);
                break;
            case R.id.btnWaiting:
                showToast("You have sent request! \nPlease wait for reply from owner of branch!");
                break;
        }
    }

    @Override
    public void joinBranchSuccess() {
        btnFollow.setVisibility(View.GONE);
        btnWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    public void joinBranchFalse() {
        showToast("False");
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
        TextView txtRelation;
        CircleImageView civProfile;
        LinearLayout nodeBg;
        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtName);
            txtDate = view.findViewById(R.id.txtDate);
            txtRelation = view.findViewById(R.id.txtRelation);
            civProfile = view.findViewById(R.id.civProfile);
            nodeBg = view.findViewById(R.id.nodeBg);
        }
    }
}
