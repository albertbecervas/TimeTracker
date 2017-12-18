package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.detail.TaskDetailActivity;
import com.ds.timetracker.ui.timer.adapter.ViewTypeAdapter;
import com.ds.timetracker.ui.timer.callback.ItemCallback;
import com.ds.timetracker.utils.AppSharedPreferences;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;
    public TextView title;
    private TextView time;

    private ImageView imageView;

    private ItemCallback mCallback;
    private Context context;

    public TaskViewHolder(View view, final Context mContext) {
        super(view);

        mCallback = (ItemCallback) mContext;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("intervals", mTask.getIntervals());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        title = view.findViewById(R.id.task_title);
        time = view.findViewById(R.id.time);
        imageView = view.findViewById(R.id.imageView);
    }

    public void setItem(Item task, final ArrayList<Item> items) {
        mTask = (Task) task;

        title.setText(task.getName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTask.isOpen()) {
                    mTask.stop();
                    imageView.setImageResource(R.drawable.ic_action_playback_play);
                    mCallback.onItemStateChanged();
                } else {
                    mTask.start();
                    imageView.setImageResource(R.drawable.ic_action_playback_pause);
                    mCallback.onItemStateChanged();
                }
            }
        });

        setTime();
    }

    private void setTime() {
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        if (mTask.getIntervals().size() == 0) return;
        Interval interval = mTask.getIntervals().get(mTask.getIntervals().size() - 1);

        long durada = mTask.getPeriod().getDuration();

        final long hores = durada / segonsPerHora;
        final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
        final long segons = durada - segonsPerHora * hores - segonsPerMinut * minuts;

        time.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
    }

}