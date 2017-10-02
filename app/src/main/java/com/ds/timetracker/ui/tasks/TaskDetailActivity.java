package com.ds.timetracker.ui.tasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.ds.timetracker.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("taskName"));
        setSupportActionBar(toolbar);

        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);

        name.setText(getIntent().getStringExtra("taskName"));
        description.setText(getIntent().getStringExtra("decription"));

        final Chronometer chronometer = findViewById(R.id.chrono);
        chronometer.setFormat("time - %s");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.start();

//                tStart = System.currentTimeMillis();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
//                tStop = System.currentTimeMillis();
            }
        });

    }
}
