package com.ds.timetracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task extends Item implements Serializable {

    private Date initialWorkingDate;
    private Date finalWorkingDate;
    private Boolean isStarted;
    private ArrayList<Interval> intervals;

    public Task() {
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isStarted = false;
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

    public Boolean getStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public void setInterval(Date startDate, Date endDate) {
        intervals.add(new Interval(startDate, endDate));
    }

    public void updateInterval(Date endDate) {
        intervals.get(intervals.size() - 1).setEndWorkingLogDate(endDate);
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

}
