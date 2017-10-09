package com.ds.timetracker.model;

import java.util.ArrayList;

public class Project extends Item{

    private ArrayList<Project> subProjects;
    private ArrayList<Task> tasks;

    public Project(String name, String description, String databasePath, String key) {
        this.setName(name);
        this.setDescription(description);
        this.setDatabasePath(databasePath);
        this.setKey(key);
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

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
