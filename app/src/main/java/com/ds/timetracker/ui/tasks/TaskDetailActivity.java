package com.ds.timetracker.ui.tasks;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.FirebaseCallback;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.model.Interval;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity implements FirebaseCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_aux);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("taskName"));
        setSupportActionBar(toolbar);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(getIntent().getStringExtra("reference"));
        FirebaseHelper mFirebase = new FirebaseHelper(this,mDatabase);
        mFirebase.getIntervals();

    }

    @Override
    public void onIntervalLoaded(ArrayList<Interval> intervals) {
        ArrayList<String> labels = new ArrayList<>();

        for (Interval interval : intervals){
            String label = interval.getStartWorkingLogDate().toString() + "--->" + interval.getEndWorkingLogDate().toString();
            labels.add(label);
        }

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter itemsAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,labels);
        listView.setAdapter(itemsAdapter);
    }

    @Override
    public void onProjectsLoaded(ArrayList<Object> items) {
        //Do nothing
    }
}
