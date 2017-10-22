package com.ds.timetracker.model;


import com.ds.timetracker.callback.IntervalCallback;
import com.ds.timetracker.callback.ItemCallback;
import com.ds.timetracker.observable.Clock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task extends Item implements Serializable, IntervalCallback {

    private ItemCallback mCallback;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Interval> intervals;

    private boolean isLimited;
    private boolean isProgrammed;
    private long maxDuration;

    public Task() {
        intervals = new ArrayList<>();
        isOpen = false;
        this.duration = 0L;
    }

    public Task(String name, String description, ItemCallback taskCallback, boolean isLimited, boolean isProgrammed) {
        this.name = name;
        this.description = description;
        intervals = new ArrayList<>();
        isOpen = false;
        this.duration = 0L;

        this.mCallback = taskCallback;

        this.isLimited = isLimited;
        this.isProgrammed = isProgrammed;
        this.maxDuration = 120L;
    }

    public void setInterval(Date startDate, Date endDate) {
        intervals.add(new Interval(startDate, endDate, this));
    }

    public void updateInterval(Date endDate) {

        Interval interval = intervals.get(intervals.size() - 1);
        interval.setEndWorkingLogDatee(endDate);
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void start() {
        if (duration == 0) this.initialWorkingDate = new Date();
        this.isOpen = true;
        setInterval(new Date(), new Date());
    }

    public void stop() {
        this.isOpen = false;
        this.finalWorkingDate = new Date();
        Interval interval = this.intervals.get(intervals.size() - 1);
        interval.setOpen(false);
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


    @Override
    public void update(Interval interval) {

        if (isLimited()) {
            if (duration <= maxDuration) {
                addDuration(Clock.CLOCK_SECONDS);
            }
        } else {
            addDuration(Clock.CLOCK_SECONDS);
        }

        mCallback.update(this);
    }
}


