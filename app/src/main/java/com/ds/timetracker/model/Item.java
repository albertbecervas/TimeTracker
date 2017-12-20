package com.ds.timetracker.model;

public class Item {

    protected String name;
    protected String description;
    protected String databasePath;
    protected String itemType;
    protected boolean isStarted;

    protected Project fatherReference;

    protected Item fatherItem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public Project getFatherReference() {
        return fatherReference;
    }

    public void setFatherReference(Project fatherReference) {
        this.fatherReference = fatherReference;
    }
}
