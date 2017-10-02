package com.ds.timetracker.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.tasks.TaskDetailActivity;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private Task mTask;
    public TextView title;

    public TaskViewHolder(View view, final Context mContext) {
        super(view);
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
    }

    public void setItem(Object task) {
        mTask = (Task) task;
        title.setText(mTask.getName());
    }

}
