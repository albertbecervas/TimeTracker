package com.ds.timetracker.model;

import com.ds.timetracker.callback.TimeCallback;

import java.util.ArrayList;
import java.util.Date;

public class Task extends Item {

    private Date initialWorkingDate;
    private Date finalWorkingDate;
    private ArrayList<Interval> taskLogs;
    private Boolean isStarted;
    private int time;

    public Task() {
        time = 0;
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
    }

    public Date getInitialWorkingDate() {
        return initialWorkingDate;
    }

    public void setInitialWorkingDate(Date initialWorkingDate) {
        this.initialWorkingDate = initialWorkingDate;
    }

    public Date getFinalWorkingDate() {
        return finalWorkingDate;
    }

    public void setFinalWorkingDate(Date finalWorkingDate) {
        this.finalWorkingDate = finalWorkingDate;
    }

    public ArrayList<Interval> getTaskLogs() {
        return taskLogs;
    }

    public void setTaskLogs(ArrayList<Interval> taskLogs) {
        this.taskLogs = taskLogs;
    }

    public Boolean getStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    //methods
    public void start(Timer timer) {
//        timer.startTimer();
    }

    public void stop() {

    }

}
