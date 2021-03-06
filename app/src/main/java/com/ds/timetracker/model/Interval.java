package com.ds.timetracker.model;

import android.support.annotation.NonNull;

import com.ds.timetracker.model.observable.Clock;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * An interval will be started and finished every time the user plays/pauses a task
 * This class is observing the clock in order to update the time of its father task
 */
public class Interval implements Serializable, Observer, Comparable {

    private static final long serialVersionUID = 1L;//Needed object identifier

    private Task task;

    private Period period;
    private boolean isOpen;

    private boolean isUpdating = false;

    public Interval(Task task) {
        this.period = new Period();
        this.isOpen = true;
        this.task = task;
        Clock.getInstance().addObserver(this);
        isUpdating = true;
    }

    public void setEndWorkingLogDatee(Date endWorkingLogDate) {
        if (period == null) return;
        this.period.setFinalWorkingDate(endWorkingLogDate);
        this.period.setDuration(calculateDuration());
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        Clock.getInstance().deleteObserver(this);
    }

    public boolean isOpen() {
        return isOpen;
    }

    private Long calculateDuration() {
        return ((period.getFinalWorkingDate().getTime() - period.getStartWorkingDate().getTime()) / 1000);
    }

    public Long getDuration() {
        return period.getDuration();
    }

    public Date getFinalDate() {
        return period.getFinalWorkingDate();
    }

    public Date getInitialDate() {
        return period.getStartWorkingDate();
    }

    public String getInitialFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return dateFormat.format(period.getStartWorkingDate());
    }

    public String getInitialFormattedHour() {
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
        return hourFormat.format(period.getStartWorkingDate());
    }

    public String getFinalFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return  dateFormat.format(period.getFinalWorkingDate());
    }

    public String getFinalFormattedHour() {
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
        return hourFormat.format(period.getFinalWorkingDate());
    }

    public String getFormattedDuration() {
        if (!isUpdating) {
            Clock.getInstance().addObserver(this);
        }

        if (period == null) return "";
        int secondsForHour = 3600;
        int secondsForMinut = 60;

        final long hours = this.getDuration() / secondsForHour;
        final long minuts = (period.getDuration() - hours * secondsForHour) / secondsForMinut;
        final long seconds = period.getDuration() - secondsForHour * hours - secondsForMinut * minuts;

        return String.valueOf(hours + "h " + minuts + "m " + seconds + "s");
    }

    @Override
    public String toString() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return dateFormat.format(this.getInitialDate()) +
                "h to " +
                dateFormat.format(this.getFinalDate()) +
                " ->" +
                this.getFormattedDuration();
    }

    @Override
    public void update(Observable o, Object date) {

        if (isOpen) {
            this.setEndWorkingLogDatee((Date) date);
            if (period.getDuration() != 0) {
                task.update(this);
            }
        }

    }

    @Override
    public int compareTo(@NonNull Object other) {
        Date thisDate = this.getFinalDate();
        Date otherDate = ((Interval) other).getFinalDate();
        if (thisDate.before(otherDate)){
            return 1;
        }else if (thisDate.after(otherDate)){
            return -1;
        }else{
            return 0;
        }
    }
}
