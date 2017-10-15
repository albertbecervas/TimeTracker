package com.ds.timetracker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.FirebaseCallback;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.model.Interval;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity implements FirebaseCallback {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_aux);

        setViews();

        ArrayList<Interval> intervals = new ArrayList<>();

        setAdapter(intervals);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(getIntent().getStringExtra("reference"));
        FirebaseHelper mFirebase = new FirebaseHelper(this, mDatabase);
        mFirebase.getIntervals();

    }

    private void setAdapter(ArrayList<Interval> intervals) {
        ArrayList<String> labels = new ArrayList<>();

        for (Interval interval : intervals) {
            String label = interval.getStartWorkingLogDate().toString() + "--->" + interval.getEndWorkingLogDate().toString()
                    + ", duration = " + interval.getFormattedDuration();
            labels.add(label);
        }

        ArrayAdapter itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(itemsAdapter);
    }

    private void setViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("taskName"));
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
    }

    @Override
    public void onIntervalLoaded(ArrayList<Interval> intervals) {
        setAdapter(intervals);
    }

    @Override
    public void onItemsLoaded(ArrayList<Object> items) {
        //Do nothing
    }
}
