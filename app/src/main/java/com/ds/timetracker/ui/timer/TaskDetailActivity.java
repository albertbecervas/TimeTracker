package com.ds.timetracker.ui.timer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Task;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {

    private ListView listView;

    private Task mTask;

    private ArrayList<Interval> intervals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mTask = (Task) bundle.getSerializable("task");
            intervals = mTask != null ? mTask.getIntervals() : new ArrayList<Interval>();

            setViews();
            setAdapter();
        }
    }

    private void setAdapter() {
        ArrayList<String> labels = new ArrayList<>();

        for (Interval interval : intervals) {
            labels.add(interval.toString());
        }

        ArrayAdapter itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(itemsAdapter);
    }

    private void setViews() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mTask.getName());

        listView = findViewById(R.id.listView);
    }
}
