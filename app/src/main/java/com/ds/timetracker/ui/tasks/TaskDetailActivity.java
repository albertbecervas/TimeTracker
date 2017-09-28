package com.ds.timetracker.ui.tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.ds.timetracker.R;

public class TaskDetailActivity extends AppCompatActivity {

    private String taskID;

    private long tStart;
    private long tStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskID = getIntent().getStringExtra("taskID");

        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        final Chronometer chronometer = findViewById(R.id.chrono);
        chronometer.setFormat("time - %s");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.start();

                tStart = System.currentTimeMillis();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                tStop = System.currentTimeMillis();
            }
        });

    }
}
