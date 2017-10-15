package com.ds.timetracker.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Project extends Item implements Serializable{

    private ArrayList<Project> subProjects;
    private ArrayList<Task> tasks;

    public Project(){
        itemType = "0";
    }

    public Project(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public ArrayList<Project> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(ArrayList<Project> subProjects) {
        this.subProjects = subProjects;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Task task) {
        tasks.add(task);
    }
}
