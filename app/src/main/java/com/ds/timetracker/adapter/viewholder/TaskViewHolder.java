package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.ItemCallback;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.TaskDetailActivity;
import com.google.firebase.database.DatabaseReference;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;
    public TextView title;
    private TextView time;

    private ItemCallback mCallback;

//    private FirebaseHelper mFirebase;

    private Boolean isProject;

    public TaskViewHolder(View view, final Context mContext, DatabaseReference reference, final Boolean project) {
        super(view);

        mCallback = (ItemCallback) mContext;
//        mFirebase = new FirebaseHelper(reference);
        this.isProject = project;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("intervals", mTask.getIntervals());
                intent.putExtras(bundle);
//                intent.putExtra("reference", mTask.getDatabasePath());
                mContext.startActivity(intent);
            }
        });

        title = view.findViewById(R.id.task_title);
        time = view.findViewById(R.id.time);
        TextView start = view.findViewById(R.id.startbutton);
        TextView stop = view.findViewById(R.id.stopbutton);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mTask.isStarted()) {
//                    Toast.makeText(mContext, "Task already started", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mTask.setStarted(true);
//                mTask.setInterval(new Date(), new Date());
//                mFirebase.setTaskStarted(mTask);
//                mCallback.onItemStateChanged();
//
//                if (isProject)
//                    mFirebase.setProjectStarted();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!mTask.isStarted()) {
//                    Toast.makeText(mContext, "Task already stopped", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mCallback.onItemStateChanged();
//                mTask.setStarted(false);
//                mTask.setFinalWorkingDate(new Date());
//                mTask.closeInterval(new Date());
//                mTask.getIntervals().get(mTask.getIntervals().size() - 1).setOpen(false);
//
//                mFirebase.setInterval(mTask);
//
//                if (isProject)
//                    mFirebase.setProjectStopped();

            }
        });
    }

    public void setItem(Item task) {
        mTask = (Task) task;


        title.setText(mTask.getName());

        setTime();
    }

    private void setTime() {
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        if (mTask.getIntervals().size() == 0) return;
        Interval interval = mTask.getIntervals().get(mTask.getIntervals().size() - 1);


//        long durada = mTask.getDurada();
//        if (interval.isOpen()) durada += interval.getDuration();
//
//        final long hores = durada / segonsPerHora;
//        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
//        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;
//
//        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
    }

}