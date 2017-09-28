package com.ds.timetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.tasks.TasksActivity;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Project> projectsList;

    public ProjectsAdapter(Context context, List<Project> projectsList) {
        this.mContext = context;
        this.projectsList = projectsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_project, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setItem(projectsList.get(position));
        holder.title.setText(projectsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public void clearAdapter() {
        int size = this.projectsList.size();
        this.projectsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setProjectsList(ArrayList<Project> projects) {
        projectsList = projects;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Project mProject;
        public TextView title;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TasksActivity.class);
                    intent.putExtra("projectID", mProject.getKey());
                    mContext.startActivity(intent);
                }
            });

            title = view.findViewById(R.id.project_title);
        }

        void setItem(Project project) {
            mProject = project;
        }
    }

}
