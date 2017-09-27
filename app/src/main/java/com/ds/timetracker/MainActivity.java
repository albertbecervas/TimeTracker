package com.ds.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ds.timetracker.adapter.ProjectsAdapter;
import com.ds.timetracker.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private FloatingActionButton addProject;
    private ProjectsAdapter mAdapter;

    private List<Project> projectsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
//        setProjectsList();
        setAdapter();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Projects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                String name = dataSnapshot.child("name").getValue(String.class);

                Project project = new Project();
                project.setName(name);
                project.setKey(key);

                projectsList.add(project);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setViews() {
        mRecyclerView = findViewById(R.id.projectsRV);
        addProject = findViewById(R.id.add_project);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateProjectActivity.class));
            }
        });
    }

//    private void setProjectsList(){
//        projectsList = new ArrayList<>();
//        projectsList.add("Project 1");
//        projectsList.add("Project 2");
//    }

    private void setAdapter(){
        mAdapter = new ProjectsAdapter(this, projectsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }
}
