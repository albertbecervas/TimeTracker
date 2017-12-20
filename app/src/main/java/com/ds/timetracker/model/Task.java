package com.ds.timetracker.model;

import com.ds.timetracker.helpers.FirebaseHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task extends Item implements Serializable {

    private Date initialWorkingDate;
    private Date finalWorkingDate;
    private ArrayList<Interval> intervals;
    private long durada; //in seconds

    private boolean isLimited;
    private boolean isProgrammed;
    private long maxDuration;

    public Task() {
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isStarted = false;
        durada = 0L;
        this.itemType = "1";
    }

    public Task(String name, String description, boolean isLimited, boolean isProgrammed, Project fatherReference) {
        this.name = name;
        this.description = description;
        this.fatherReference = fatherReference;
        initialWorkingDate = new Date();
        finalWorkingDate = new Date();
        intervals = new ArrayList<>();
        isStarted = false;
        durada = 0L;
        this.itemType = "1";

        this.isLimited = isLimited;
        this.isProgrammed = isProgrammed;
        this.maxDuration = 120L;
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

    public void setInterval(Date startDate, Date endDate) {
        intervals.add(new Interval(startDate, endDate));
    }

    public void updateInterval(Date endDate) {

        Interval interval = intervals.get(intervals.size() - 1);
        interval.setEndWorkingLogDatee(endDate);
    }

    public void closeInterval(Date endDate) {
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




    public boolean isLimited() {
        return isLimited;
    }

    public void setLimited(boolean limited) {
        isLimited = limited;
    }

    public boolean isProgrammed() {
        return isProgrammed;
    }

    public void setProgrammed(boolean programmed) {
        isProgrammed = programmed;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }
}
