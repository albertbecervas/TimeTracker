package com.ds.timetracker.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Albert
 * @invariant duration >=0
 */
public class Period implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Date startWorkingDate;
    private Date finalWorkingDate;
    private long duration;

    public Period() {
        duration = 0L;
        this.startWorkingDate = new Date();
        //TODO check if works
        this.finalWorkingDate = new Date();
    }

    public Date getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(Date startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public Date getFinalWorkingDate() {
        return finalWorkingDate;
    }

    public void setFinalWorkingDate(Date finalWorkingDate) {
        this.finalWorkingDate = finalWorkingDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void addDuration(long duration) {
        this.duration += duration;
    }

    public String getInitialFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return dateFormat.format(startWorkingDate);
    }

    public String getInitialFormattedHour() {
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
        return hourFormat.format(startWorkingDate);
    }

    public String getFinalFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return dateFormat.format(finalWorkingDate);
    }

    public String getFinalFormattedHour(){
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
        return hourFormat.format(finalWorkingDate);
    }
}
