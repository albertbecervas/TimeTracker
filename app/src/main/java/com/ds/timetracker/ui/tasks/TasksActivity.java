package com.ds.timetracker.ui.tasks;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ds.timetracker.R;
import com.ds.timetracker.adapter.TasksAdapter;
import com.ds.timetracker.callback.TasksCallback;
import com.ds.timetracker.helpers.TasksHelper;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.CreateProjectActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity implements TasksCallback {

    private RecyclerView mRecyclerView;
    private TasksAdapter mAdapter;

    private TasksHelper mTasks;

    private CircularProgressView mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        String projectID = getIntent().getStringExtra("projectID");

        setViews(projectID);
        setAdapter();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mTasks = new TasksHelper(this, mDatabase, projectID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clearAdapter();
        mTasks.getTasks();
    }

    private void setViews(final String projectID) {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.startAnimation();

        mRecyclerView = findViewById(R.id.tasksRV);
        FloatingActionButton addProject = findViewById(R.id.add_project);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TasksActivity.this, CreateTaskActivity.class);
                i.putExtra("projectID", projectID);
                startActivity(i);
            }
        });
    }

    private void setAdapter() {
        List<Task> tasksList = new ArrayList<>();
        mAdapter = new TasksAdapter(this, tasksList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onTasksLoaded(ArrayList<Task> tasks) {
        mProgressBar.setVisibility(View.GONE);
        if (tasks == null) return;
        mAdapter.setTasksList(tasks);
        mRecyclerView.setAdapter(mAdapter);
    }
}
