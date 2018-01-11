package com.ds.timetracker.model;

/**
 * Helper class to keep the settings active in runtime
 */
public class Settings {

    private static Settings instance = null;

    private String sortBy = "name";
    private int clockSeconds = 1;

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

    public void setClockSeconds(int clockSeconds){
        this.clockSeconds = clockSeconds;
    }

    public int getClockSeconds(){
        return clockSeconds;
    }
}
