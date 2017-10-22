package com.ds.timetracker.model;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String description;
    protected String itemType;

    protected boolean isOpen;
    protected long duration;
    protected Date initialWorkingDate;
    protected Date finalWorkingDate;

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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void addDuration(long duration) {
        this.duration += duration;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Date getInitialWorkingDate() {
        return initialWorkingDate;
    }

    public void setInitialWorkingDate(Date initialWorkingDate) {
        this.initialWorkingDate = initialWorkingDate;
    }

    public Date getFinalWorkingDate() {
        return finalWorkingDate;
    }

    public void setFinalWorkingDate(Date finalWorkingDate) {
        this.finalWorkingDate = finalWorkingDate;
    }


    public String getFormattedTable() {
        int segonsPerHora = 3600;
        int segonsPerMinut = 60;

        final long hores = duration / segonsPerHora;
        final long minuts = (duration - hores * segonsPerHora) / segonsPerMinut;
        final long segons = duration - segonsPerHora * hores - segonsPerMinut * minuts;

        return  String.valueOf(getName() + " ---> "
                + "duration = " + String.valueOf(hores + "h " + minuts + "m " + segons + "s")) + " |"
                + "from: " + initialWorkingDate + " | "
                + "to: " + finalWorkingDate + " |\n ";

    }
}

