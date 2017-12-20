package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.timer.ProjectDetailActivity;
import com.ds.timetracker.ui.timer.callback.ItemCallback;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    private Project mProject;
    public TextView title;
    private TextView time;

    private ItemCallback mCallback;

    public ProjectViewHolder(View view, final Context mContext) {
        super(view);

        this.mCallback = (ItemCallback) mContext;

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
                mCallback.onEditProject(getAdapterPosition());
            }
        });

        title = view.findViewById(R.id.project_title);
        time = view.findViewById(R.id.time);
    }

    public void setItem(Item project) {
        mProject = (Project) project;
        title.setText(project.getName());

        setTime();

    }

    private void setTime() {
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        if (mProject.getPeriod().getFinalWorkingDate() == null) return;
        long durada = (mProject.getPeriod().getFinalWorkingDate().getTime() - mProject.getPeriod().getStartWorkingDate().getTime()) / 1000;

        final long hores = durada / segonsPerHora;
        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;

        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
    }

}
