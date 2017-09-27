package com.ds.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds.timetracker.R;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> projectsList;

    public ProjectsAdapter(Context context, List<String> projectsList) {
        this.mContext = context;
        this.projectsList = projectsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_project,parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(projectsList.get(position));
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.project_title);
        }
    }

}
