package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.ItemStarted;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.Timer;
import com.ds.timetracker.ui.tasks.TaskDetailActivity;

import java.util.Date;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;
    public TextView title;
    private Button startStop;
    private TextView time;

    private ItemStarted mCallback;

    public TaskViewHolder(View view, final Context mContext) {
        super(view);

        mCallback = (ItemStarted) mContext;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                intent.putExtra("projectID", mTask.getKey());
                intent.putExtra("taskName", mTask.getName());
                intent.putExtra("taskDescription", mTask.getDescription());
                mContext.startActivity(intent);
            }
        });

        title = view.findViewById(R.id.task_title);
        time = view.findViewById(R.id.time);
        startStop = view.findViewById(R.id.startbutton);

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startStop.getText().equals("start")){
                    startStop.setText("start");
                    startStop.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_700));
                    mTask.setTime(0);
                    mTask.setInitialWorkingDate(new Date());
                    mTask.setFinalWorkingDate(new Date());
                    mCallback.onItemStarted(mTask.getName(), getAdapterPosition(), mTask, TaskViewHolder.this);
                }
//                else {
//                    startStop.setText("start");
//                    startStop.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_700));
//                }
            }
        });
    }

    public void setItem(Object task) {
        mTask = (Task) task;
        title.setText(mTask.getName());

        int segonsPerHora=3600;
        int segonsPerMinut=60;

        long durada = (mTask.getFinalWorkingDate().getTime() - mTask.getInitialWorkingDate().getTime())/1000;

        final long hores = durada / segonsPerHora;
        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
        final long segons = durada - segonsPerHora * hores- segonsPerMinut * minuts;

        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
    }

}
