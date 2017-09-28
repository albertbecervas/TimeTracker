package com.ds.timetracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ds.timetracker.R;
import com.ds.timetracker.adapter.ProjectsAdapter;
import com.ds.timetracker.callback.ProjectCallback;
import com.ds.timetracker.helpers.ProjectHelper;
import com.ds.timetracker.model.Project;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProjectsActivity extends AppCompatActivity implements ProjectCallback {

    private RecyclerView mRecyclerView;
    private ProjectsAdapter mAdapter;

    private ProjectHelper mProjects;

    private CircularProgressView mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setAdapter();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mProjects = new ProjectHelper(this, mDatabase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clearAdapter();
        mProjects.getProjects();
    }

    private void setViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.startAnimation();

        mRecyclerView = findViewById(R.id.projectsRV);
        FloatingActionButton addProject = findViewById(R.id.add_project);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectsActivity.this, CreateProjectActivity.class));
            }
        });
    }

    private void setAdapter() {
        List<Project> projectsList = new ArrayList<>();
        mAdapter = new ProjectsAdapter(this, projectsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onProjectsLoaded(ArrayList<Project> projects) {
        mAdapter.setProjectsList(projects);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
    }
}
