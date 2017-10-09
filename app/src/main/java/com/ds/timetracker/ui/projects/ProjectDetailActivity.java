package com.ds.timetracker.ui.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ds.timetracker.R;
import com.ds.timetracker.adapter.ViewTypeAdapter;
import com.ds.timetracker.callback.FirebaseCallback;
import com.ds.timetracker.callback.TimeCallback;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.ui.tasks.CreateTaskActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class ProjectDetailActivity extends AppCompatActivity implements FirebaseCallback, TimeCallback {

    private RecyclerView mRecyclerView;
    private ViewTypeAdapter mAdapter;

    private FirebaseHelper mProjects;

    private CircularProgressView mProgressBar;

    private String reference = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        setViews();
        setAdapter();

        if (getIntent().hasExtra("reference"))
            reference = getIntent().getStringExtra("reference");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(reference);
        mProjects = new FirebaseHelper(this, mDatabase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clearAdapter();
        mProjects.getProjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_project:
                Intent i = new Intent(this, CreateProjectActivity.class);
                i.putExtra("reference", reference);
                startActivity(i);
                break;
            case R.id.add_task:
                Intent i2 = new Intent(this, CreateTaskActivity.class);
                i2.putExtra("reference", reference);
                startActivity(i2);
                break;
        }
        return true;
    }

    private void setViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.startAnimation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_add));
        toolbar.setTitle(getIntent().getStringExtra("projectName"));
        setSupportActionBar(toolbar);


        mRecyclerView = findViewById(R.id.projectsRV);
        FloatingActionButton addProject = findViewById(R.id.fab);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ProjectDetailActivity.this, CreateProjectActivity.class));
                Toast.makeText(ProjectDetailActivity.this, "I want to be a menu :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        ArrayList<Object> itemList = new ArrayList<>();
        mAdapter = new ViewTypeAdapter(this, itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onProjectsLoaded(ArrayList<Object> items) {
        mAdapter.setItemsList(items);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void newSecond(Date date) {

    }
}
