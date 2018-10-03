package com.senior.project.genealogy.view.fragment.genealogy.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Genealogy;
import com.senior.project.genealogy.view.fragment.genealogy.GenealogyDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewItemGenealogyAdapter extends RecyclerView.Adapter<RecyclerViewItemGenealogyAdapter.RecyclerViewHolder>{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<Genealogy> data = new ArrayList<>();

    public RecyclerViewItemGenealogyAdapter(Context mContext, FragmentManager mFragmentManager, List<Genealogy> data) {
        this.mContext = mContext;
        this.data = data;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_genealogy, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.txtGenealogyName.setText(data.get(position).getName());

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                GenealogyDetailFragment mFragment = new GenealogyDetailFragment();

                mFragmentManager.beginTransaction().replace(R.id.genealogy_container, mFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtGenealogyName;
        TextView txtGenealogyOwner;
        TextView txtGenealogyDate;
        LinearLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtGenealogyName = (TextView) itemView.findViewById(R.id.txtGenealogyName);
            txtGenealogyOwner = (TextView) itemView.findViewById(R.id.txtGenealogyOwner);
            txtGenealogyDate = (TextView) itemView.findViewById(R.id.txtGenealogyDate);
            line = (LinearLayout) itemView.findViewById(R.id.line);
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String username);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
