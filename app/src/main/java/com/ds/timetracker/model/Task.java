package com.ds.timetracker.model;

import com.ds.timetracker.model.observable.Clock;
import com.ds.timetracker.utils.Constants;

import java.util.ArrayList;
import java.util.Date;


public class Task extends Item {

    private Project project;

    private static final long serialVersionUID = 1L;//Needed object identifier
    private ArrayList<Interval> intervals;

    public Task(String name, String description, Project project) {
        super(name, description, new Period(), Constants.TASK);

        this.isOpen = false;
        this.intervals = new ArrayList<>();

        this.project = project;
    }

    public void setInterval() {
        intervals.add(new Interval(this));
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void start() {
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());//The first time we start a ic_task we set it's start working date and it will never be updated
        this.isOpen = true;
        if (project != null) project.start();//We check if the ic_task is in the main items list
        setInterval();
    }

    public void stop() {
        this.isOpen = false;
        this.period.setFinalWorkingDate(new Date());//Every time we stop an item inside the project, we update the finalWorkingDate
        if (project != null) project.stop();//We check if the ic_task is in the main items list
        Interval interval = this.intervals.get(intervals.size() - 1);//getting the last interval
        interval.setOpen(false);
    }
    
    public void update(Interval interval){
        period.addDuration(Clock.CLOCK_SECONDS);
        if (project != null) project.update(this);//We check if the ic_task is in the main items list
    }
}


