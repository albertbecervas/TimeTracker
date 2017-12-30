package com.ds.timetracker.model;


import com.ds.timetracker.model.observable.Clock;
import com.ds.timetracker.utils.Constants;

import java.util.ArrayList;
import java.util.Date;


public class Project extends Item {

    //father project
    private Project project;

    private ArrayList<Item> items;

    public Project(String name, String description, int color ,Project project) {
        super(name, description, new Period(), Constants.PROJECT, color);

        this.items = new ArrayList<>();

        this.project = project; //callback to update the father
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void newTask(String name, String description, int color) {
        items.add(new Task(name, description,color, this));
    }

    public void newProject(String name, String description, int color) {
        items.add(new Project(name, description, color,this));
    }

    public void start() {
        //The first time we start a ic_task we set it's start working date and it will never be updated
        if (period.getDuration() == 0) this.period.setStartWorkingDate(new Date());
        this.isOpen = true;
    }

    public void stop() {
        //Every time we stop an item inside the project, we update the finalWorkingDate
        this.period.setFinalWorkingDate(new Date());
        this.isOpen = false;
    }

    public void deleteItems(){
        items.clear();
    }

    public void update(Item item) {
        period.addDuration(Clock.CLOCK_SECONDS);

        if (project != null) {
            project.update(this);//updating the father
        }
    }
}

