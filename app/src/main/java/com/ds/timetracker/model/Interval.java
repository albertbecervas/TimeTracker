package com.ds.timetracker.model;

import com.ds.timetracker.model.observable.Clock;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


public class Interval implements Serializable, Observer {

    private static final long serialVersionUID = 1L;//Needed object identifier

    private Task task;

    private Period period;
    private boolean isOpen;

    public Interval(Task task) {
        this.period = new Period();
        this.isOpen = true;
        this.task = task;
        Clock.getInstance().addObserver(this);
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

    public String getFormattedDuration(){
        if (period == null) return "";
        int secondsForHour = 3600;
        int secondsForMinut = 60;

        final long hours = this.getDuration() / secondsForHour;
        final long minuts = (period.getDuration() - hours * secondsForHour) / secondsForMinut;
        final long seconds = period.getDuration() - secondsForHour * hours - secondsForMinut * minuts;

        return   String.valueOf(hours + "h " + minuts + "m " + seconds + "s");
    }

    @Override
    public String toString() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return dateFormat.format(this.getInitialDate()) +
                "h to "+
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
}
