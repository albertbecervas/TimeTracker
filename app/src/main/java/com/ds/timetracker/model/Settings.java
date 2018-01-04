package com.ds.timetracker.model;

/**
 * Created by arnau on 04/01/2018.
 */

public class Settings {

    private static Settings instance = null;

    private String sortBy = "name";

    private Settings() {

    }

    public static Settings getInstance(){
        if (instance == null) instance = new Settings();
        return instance;
    }

    public void setSortBy(String sortBy){
        this.sortBy = sortBy;
    }

    public String getSortBy(){
        return sortBy;
    }
}
