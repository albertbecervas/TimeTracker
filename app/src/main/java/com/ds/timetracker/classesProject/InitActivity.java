package com.ds.timetracker.classesProject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.TimeCallback;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.Timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InitActivity extends AppCompatActivity implements View.OnClickListener, TimeCallback {

    private Timer timer;

    private Task first;

    private Task second;

    private TextView time1;
    private TextView time2;

    private ArrayList<Task> tasks;
    private ArrayList<String> activeTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        setViews();

        timer = new Timer(this);


        first = setTask("firstTask", "vds");

        second = setTask("secondTask", "vsdf");

        tasks = new ArrayList<>();
        tasks.add(first);
        tasks.add(second);

        activeTasks = new ArrayList<>();

    }

    @NonNull
    private Task setTask(String name, String description) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        return task;
    }

    private void setViews() {
        Button startFirst = findViewById(R.id.startbutton1);
        Button stopFirst = findViewById(R.id.stopbutton1);
        Button startSecond = findViewById(R.id.startbutton2);
        Button stopSecond = findViewById(R.id.stopbutton2);
        Button restart = findViewById(R.id.restart);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);

        startFirst.setOnClickListener(this);
        stopFirst.setOnClickListener(this);
        startSecond.setOnClickListener(this);
        stopSecond.setOnClickListener(this);
        restart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startbutton1:
                first.setInitialWorkingDate(new Date());
                activeTasks.add("first");
                break;
            case R.id.stopbutton1:
                activeTasks.remove("first");
                first.setFinalWorkingDate(new Date());
                break;
            case R.id.startbutton2:
                second.setInitialWorkingDate(new Date());
                activeTasks.add("second");
                break;
            case R.id.stopbutton2:
                activeTasks.remove("second");
                second.setFinalWorkingDate(new Date());
                break;
            case R.id.restart:
                first.setInitialWorkingDate(new Date());
                second.setInitialWorkingDate(new Date());
        }
    }

    @Override
    public void newSecond(final Date date) {

        int segonsPerHora=3600;
        int segonsPerMinut=60;

        for (final String task : activeTasks) {

            final int index = getIndex(task);
            final Task task1 = tasks.get(index);

            task1.setFinalWorkingDate(date);
            long durada = (task1.getFinalWorkingDate().getTime() - task1.getInitialWorkingDate().getTime())/1000;

            final long hores = durada / segonsPerHora;
            final long minuts = (durada - hores * segonsPerHora) / segonsPerMinut;
            final long segons = durada - segonsPerHora * hores- segonsPerMinut * minuts;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        time1.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
                    }
                    if (index == 1)
                        time2.setText(String.valueOf(hores + "h " + minuts + "m " + segons + "s"));
                }
            });
        }
    }

    private int getIndex(String task) {
        int index = 0;
        if (task.equals("first")) index = 0;
        if (task.equals("second")) index = 1;
        return index;
    }
}
