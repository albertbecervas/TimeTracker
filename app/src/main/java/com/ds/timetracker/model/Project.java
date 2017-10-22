package com.ds.timetracker.model;


import com.ds.timetracker.callback.ItemCallback;
import com.ds.timetracker.observable.Clock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Project extends Item implements Serializable, ItemCallback {

    private ItemCallback itemCallback;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<Item> items;

    public Project() {
        this.itemType = "0";
        this.duration = 0L;
        this.items = new ArrayList<Item>();
    }

    public Project(String name, String description, Project project) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<Item>();
        this.itemType = "0";
        this.duration = 0L;
        this.itemCallback = project;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void newTask(String name, String description) {
        Task task = new Task(name, description, this, false, false);
        items.add(task);
    }

    public void newProject(String name, String description) {
        Project project = new Project(name, description, this);
        items.add(project);
    }

    public void startTask(int position) {
        if (duration == 0) this.initialWorkingDate = new Date();
        ((Task) items.get(position)).start();
        this.isOpen = true;
    }

    public void stopTask(int position) {
        ((Task) items.get(position)).stop();
        this.finalWorkingDate = new Date();
        this.isOpen = false;
    }


    @Override
    public void update(Item item) {
        addDuration(Clock.CLOCK_SECONDS);

        if (itemCallback != null) {
            itemCallback.update(this);
        }
    }

}

