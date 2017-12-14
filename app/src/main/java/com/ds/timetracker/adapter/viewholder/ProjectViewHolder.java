package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.MainActivity;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    private Project mProject;
    public TextView title;
    private TextView time;

    public ProjectViewHolder(View view, final Context mContext) {
        super(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
//                intent.putExtra("reference", mProject.getDatabasePath());
                intent.putExtra("isProject", true);
                mContext.startActivity(intent);
            }
        });

        title = view.findViewById(R.id.project_title);
        time = view.findViewById(R.id.time);
    }

    public void setItem(Item project) {
        mProject = (Project) project;
        title.setText(mProject.getName());

        setTime();

    }

    private void setTime() {
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

//        if (mProject.getFinalWorkingDate() == null) return;
//        long durada = (mProject.getFinalWorkingDate().getTime() - mProject.getInitialWorkingDate().getTime()) / 1000;
//
//        final long hores = durada / segonsPerHora;
//        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
//        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;
//
//        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
    }

}
