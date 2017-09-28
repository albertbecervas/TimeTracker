package com.ds.timetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.tasks.TaskDetailActivity;
import com.ds.timetracker.ui.tasks.TasksActivity;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {

    private Context mContext;
    private List<Task> tasksList;

    public TasksAdapter(Context context, List<Task> projectsList) {
        this.mContext = context;
        this.tasksList = projectsList;
    }

    @Override
    public TasksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TasksAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_project, parent, false));
    }

    @Override
    public void onBindViewHolder(TasksAdapter.MyViewHolder holder, int position) {
        holder.setItem(tasksList.get(position));
        holder.title.setText(tasksList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void clearAdapter() {
        int size = this.tasksList.size();
        this.tasksList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setTasksList(ArrayList<Task> tasks) {
        tasksList = tasks;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Task mTask;
        public TextView title;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra("taskID", mTask.getKey());
                    mContext.startActivity(intent);
                }
            });

            title = view.findViewById(R.id.project_title);
        }

        void setItem(Task task) {
            mTask = task;
        }
    }
}
