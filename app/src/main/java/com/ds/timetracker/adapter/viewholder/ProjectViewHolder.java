package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.projects.ProjectDetailActivity;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    private Project mProject;
    public TextView title;

    public ProjectViewHolder(View view, final Context mContext) {
        super(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProjectDetailActivity.class);
                intent.putExtra("reference", mProject.getmDatabase());
                intent.putExtra("projectName", mProject.getName());
                mContext.startActivity(intent);
            }
        });

        title = view.findViewById(R.id.project_title);
    }

    public void setItem(Object project) {
        mProject = (Project) project;
        title.setText(mProject.getName());
    }

}
