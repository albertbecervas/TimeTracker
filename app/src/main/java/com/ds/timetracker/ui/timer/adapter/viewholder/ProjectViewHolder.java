package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.timer.callback.ItemCallback;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    private Project mProject;
    public TextView title;
    private TextView time;

    private ItemCallback mCallback;

    public ProjectViewHolder(View view, final Context mContext, final ItemCallback callback) {
        super(view);

        this.mCallback = callback;

        final ConstraintLayout deleteLayout = view.findViewById(R.id.delete_container);
        final ConstraintLayout editLayout = view.findViewById(R.id.edit_container);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteLayout.getVisibility() == View.VISIBLE){
                    deleteLayout.setVisibility(View.GONE);
                    editLayout.setVisibility(View.GONE);
                } else {
                    mCallback.onProjectItemSelected(getAdapterPosition());
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (deleteLayout.getVisibility() == View.VISIBLE){
                    deleteLayout.setVisibility(View.GONE);
                    editLayout.setVisibility(View.GONE);
                } else {
                    deleteLayout.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onDeleteItem(getAdapterPosition());
            }
        });

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onEditProject(getAdapterPosition(), mProject);
            }
        });

        title = view.findViewById(R.id.project_title);
        time = view.findViewById(R.id.time);
    }

    public void setItem(Item project) {
        mProject = (Project) project;
        title.setText(project.getName());

        time.setText(mProject.getFormattedDuration());

    }
}