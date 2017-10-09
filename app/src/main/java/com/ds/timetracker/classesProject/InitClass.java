package com.ds.timetracker.classesProject;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ds.timetracker.callback.TimeCallback;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.Timer;

import java.util.ArrayList;
import java.util.Date;

public class InitClass implements TimeCallback {

    ArrayList<Task> tasks;

    ArrayList<Integer> activeTasks;

    InitClass() {

        //start counting seconds
        Timer timer = new Timer(this);


        Task first = setTask("firstTask", "vds");

        Task second = setTask("secondTask", "vsdf");

        tasks = new ArrayList<>();
        tasks.add(first);
        tasks.add(second);

        activeTasks = new ArrayList<>();

        first.start(timer);
        activeTasks.add(0);
        second.start(timer);
        activeTasks.add(1);
    }

    @NonNull
    private Task setTask(String name, String description) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        return task;
    }

    @Override
    public void newSecond(Date date) {
        for (int task : activeTasks) {
//            tasks.get(task).setTime(i);
            Log.d("Trackerr---->" + tasks.get(task).getName(), "newSecond: " + tasks.get(task).getTime());
        }
    }
}
