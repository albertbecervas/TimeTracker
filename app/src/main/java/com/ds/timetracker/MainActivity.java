package com.ds.timetracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ds.timetracker.adapter.ProjectsAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private ProjectsAdapter mAdapter;

    private List<String> projectsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setProjectsList();
        setAdapter();
    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.projectsRV);
    }

    private void setProjectsList(){
        projectsList = new ArrayList<>();
        projectsList.add("Project 1");
        projectsList.add("Project 2");
    }

    private void setAdapter(){
        mAdapter = new ProjectsAdapter(this, projectsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }
}
