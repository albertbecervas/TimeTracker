package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.timer.TaskDetailActivity;
import com.ds.timetracker.ui.timer.callback.ItemCallback;

import java.util.ArrayList;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;
    public TextView title;
    private TextView time;

    private ImageView imageView;

    private ItemCallback mCallback;

    public TaskViewHolder(View view, final Context mContext, final ItemCallback callback) {
        super(view);

        this.mCallback = callback;

        final ConstraintLayout deleteLayout = view.findViewById(R.id.delete_container);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteLayout.getVisibility() == View.VISIBLE) {
                    deleteLayout.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(mContext, TaskDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("task", mTask);
                    intent.putExtras(bundle);
                    intent.putExtra("position", getAdapterPosition());
                    mCallback.onEditTask(getAdapterPosition(),intent);
                }

            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (deleteLayout.getVisibility() == View.VISIBLE){
                    deleteLayout.setVisibility(View.GONE);
                } else {
                    deleteLayout.setVisibility(View.VISIBLE);
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

        title = view.findViewById(R.id.task_title);
        time = view.findViewById(R.id.time);
        imageView = view.findViewById(R.id.imageView);
    }

    public void setItem(Item task) {
        mTask = (Task) task;

        title.setText(mTask.getName());

        if (mTask.isOpen()){
            imageView.setImageResource(R.drawable.ic_action_playback_pause);
        } else {
            imageView.setImageResource(R.drawable.ic_action_playback_play);
        }

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
        if (mTask.getIntervals().size() == 0) return;
        time.setText(mTask.getFormattedDuration());
    }

}