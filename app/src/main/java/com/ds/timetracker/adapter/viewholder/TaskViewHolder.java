package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.ItemStarted;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.tasks.TaskDetailActivity;

import java.util.Date;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;
    public TextView title;
    private TextView time;

    private ItemStarted mCallback;

    public TaskViewHolder(View view, final Context mContext) {
        super(view);

        mCallback = (ItemStarted) mContext;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Intervals", mTask.getIntervals());
                intent.putExtras(bundle);
                intent.putExtra("reference", mTask.getDatabasePath());
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
                mTask.setStarted(true);
                mTask.setInitialWorkingDate(new Date());
                mTask.setInterval(new Date(),new Date());
                mCallback.onItemStarted(getAdapterPosition());
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask.setStarted(false);
                mTask.setFinalWorkingDate(new Date());
                mTask.updateInterval(new Date());
                FirebaseHelper mFirebase = new FirebaseHelper();

                mFirebase.setInterval(mTask);
                mCallback.onItemRemoved(getAdapterPosition());
            }
        });
    }

    public void setItem(Object task) {
        mTask = (Task) task;
        title.setText(mTask.getName());

        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        long durada = (mTask.getFinalWorkingDate().getTime() - mTask.getInitialWorkingDate().getTime()) / 1000;

        final long hores = durada / segonsPerHora;
        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;

        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));

    }

}
