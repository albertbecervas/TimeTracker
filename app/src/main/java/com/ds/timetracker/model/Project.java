package com.ds.timetracker.model;

import java.io.Serializable;
import java.util.Date;

public class Project extends Item implements Serializable {

    private long duration;
    private Date initialWorkingDate;
    private Date finalWorkingDate;

    public Project() {
        this.itemType = "0";
        this.duration = 0L;
        this.isStarted = false;
    }

    public Project(String name, String description) {
        this.setName(name);
        this.setDescription(description);
        this.itemType = "0";
        this.duration = 0L;
        this.isStarted = false;
    }


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
        duration += (getFinalWorkingDate().getTime() - getInitialWorkingDate().getTime()) / 1000;
    }
}
