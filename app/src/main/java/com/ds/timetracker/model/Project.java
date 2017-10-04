package com.ds.timetracker.model;

import java.util.ArrayList;

public class Project {

    private String name;
    private String key;
    private String description;
    private String mDatabase;
    private ArrayList<Project> subProjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Project> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(ArrayList<Project> subProjects) {
        this.subProjects = subProjects;
    }

    public String getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(String mDatabase) {
        this.mDatabase = mDatabase;
    }

    //methods
    public void setProject(){

    }
}
