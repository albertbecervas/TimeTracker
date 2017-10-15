package com.ds.timetracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task extends Item implements Serializable {

    private Date initialWorkingDate;
    private Date finalWorkingDate;
    private Boolean isStarted;
    private ArrayList<Interval> intervals;
    private long durada;

    public Task(){
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isStarted = false;
        durada = 0L;
        this.itemType = "1";
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isStarted = false;
        durada = 0L;
        this.itemType = "1";
    }

    public Date getInitialWorkingDate() {
        return initialWorkingDate;
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
        Interval interval = intervals.get(intervals.size() - 1);
        interval.setEndWorkingLogDatee(endDate);
    }

    public void closeInterval(Date endDate){
        Interval interval = intervals.get(intervals.size() - 1);
        interval.setEndWorkingLogDatee(endDate);
        setDurada(interval.getDuration());
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public long getDurada() {
        return durada;
    }

    public void setDurada(long durada) {
        this.durada += durada;
    }
}
