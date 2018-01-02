package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.timer.TaskDetailActivity;
import com.ds.timetracker.ui.timer.callback.ItemCallback;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;

    public TextView title;
    private TextView time;
    private TextView startStop;
    private LinearLayout color;

    private ItemCallback mCallback;

    private Context mContext;

    public TaskViewHolder(View view, final Context mContext, final ItemCallback callback) {
        super(view);

        this.mCallback = callback;

        this.mContext = mContext;

        final ConstraintLayout deleteLayout = view.findViewById(R.id.delete_container);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteLayout.getVisibility() == View.VISIBLE) {
                    deleteLayout.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra("position", getAdapterPosition());
                    mCallback.onEditTask(getAdapterPosition(), intent);
                }

            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (deleteLayout.getVisibility() == View.VISIBLE) {
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
        startStop = view.findViewById(R.id.start_pause);
        color = view.findViewById(R.id.colorLayout);
    }

    public void setItem(Item task) {
        mTask = (Task) task;

        title.setText(mTask.getName());

        if (mTask.isOpen()) {
            startStop.setText("PAUSE");
            startStop.setTextColor(mContext.getResources().getColor(R.color.md_orange_600));
        } else {
            startStop.setText("START");
            startStop.setTextColor(mContext.getResources().getColor(R.color.md_green_600));
        }

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTask.isOpen()) {
                    mTask.stop();
                    startStop.setText("START");
                    startStop.setTextColor(mContext.getResources().getColor(R.color.md_green_600));
                    mCallback.onItemStateChanged();
                } else {
                    mTask.start();
                    startStop.setText("PAUSE");
                    startStop.setTextColor(mContext.getResources().getColor(R.color.md_orange_600));
                    mCallback.onItemStateChanged();
                }
            }
        });

        setColor(mTask);

        setTime();
    }

    private void setColor(Task mTask) {

        int colorId = mTask.getColor();

        switch (colorId) {
            case R.drawable.red:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_800));
                break;
            case R.drawable.blue:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_blue_800));
                break;
            case R.drawable.green:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_800));
                break;
            default:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_800));
                break;
        }

    }

    private void setTime() {
        if (mTask.getIntervals().size() == 0) return;
        time.setText(mTask.getFormattedDuration());
    }

}