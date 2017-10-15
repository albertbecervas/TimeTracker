package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.ItemStarted;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.TaskDetailActivity;

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
                bundle.putSerializable("intervals", mTask.getIntervals());
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
                if (mTask.getStarted()) {
                    Toast.makeText(mContext,"Task already started",Toast.LENGTH_SHORT).show();
                    return;
                }
                mTask.setStarted(true);
                mTask.setInterval(new Date(),new Date());
                mCallback.onItemStarted(getAdapterPosition());
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTask.getStarted()){
                    Toast.makeText(mContext,"Task already stopped",Toast.LENGTH_SHORT).show();
                    return;
                }
                mCallback.onItemRemoved(getAdapterPosition());
                mTask.setStarted(false);
                mTask.setFinalWorkingDate(new Date());
                mTask.closeInterval(new Date());
                mTask.getIntervals().get(mTask.getIntervals().size() - 1).setOpen(false);

                FirebaseHelper mFirebase = new FirebaseHelper();
                mFirebase.setInterval(mTask);
            }
        });
    }

    public void setItem(Object task) {
        mTask = (Task) task;
        title.setText(mTask.getName());

        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        if (mTask.getIntervals().size() == 0) return;
        Interval interval = mTask.getIntervals().get(mTask.getIntervals().size()-1);


        long durada = mTask.getDurada();
        if (interval.isOpen()) durada += interval.getDuration();

        final long hores = durada / segonsPerHora;
        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;

        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
    }

}
