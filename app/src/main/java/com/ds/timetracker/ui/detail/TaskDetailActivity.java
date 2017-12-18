package com.ds.timetracker.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.timer.callback.FirebaseCallback;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        ArrayList<Interval> intervals = new ArrayList<>();

        setViews();

        setAdapter(intervals);

    }

    private void setAdapter(ArrayList<Interval> intervals) {
        ArrayList<String> labels = new ArrayList<>();

        ArrayAdapter itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(itemsAdapter);
    }

    private void setViews() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(getIntent().getStringExtra("taskName"));
//        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);
    }
}
