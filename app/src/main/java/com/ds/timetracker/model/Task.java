package com.ds.timetracker.model;

import android.util.Log;

import com.ds.timetracker.callback.TimeCallback;

import java.util.ArrayList;

public class Task implements TimeCallback{

    private String key;
    private String name;
    private String description;
    private String initialWorkingDate;
    private String finalWorkingDate;
    private ArrayList<TaskLog> taskLogs;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitialWorkingDate() {
        return initialWorkingDate;
    }

    public void setInitialWorkingDate(String initialWorkingDate) {
        this.initialWorkingDate = initialWorkingDate;
    }

    public String getFinalWorkingDate() {
        return finalWorkingDate;
    }

    public void setFinalWorkingDate(String finalWorkingDate) {
        this.finalWorkingDate = finalWorkingDate;
    }

    public ArrayList<TaskLog> getTaskLogs() {
        return taskLogs;
    }

    public void setTaskLogs(ArrayList<TaskLog> taskLogs) {
        this.taskLogs = taskLogs;
    }


    //methods
    public void start(){
        new Timer(this);
    }

    public void stop(){

    }

    @Override
    public void newSecond(int i) {
        Log.d("task--->" + getName(), "newSecond: " + i);
    }
}
