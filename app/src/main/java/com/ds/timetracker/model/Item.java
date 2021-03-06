package com.ds.timetracker.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Item class contains the basic information of a time tracker item whether it is a ic_task or a project.
 * <p>
 * Item implements Serializable in order to be saved as object in a file because we want to keep
 * the item tree state.
 * <p>
 * This class is public because will be called from other Classes
 */

public class Item implements Serializable, Comparable {

    protected final String type;

    protected String name;
    protected String description;
    protected boolean isOpen;
    protected Period period;

    protected int color;

    public Item(String name, String description, Period period, String type, int color) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.period = period;
        this.color = color;
    }

    //getters and setters
    public String getType() {
        return type;
    }

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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getFormattedDuration() {
        if (period == null) return "";
        int secondsForHour = 3600;
        int secondsForMinut = 60;

        final long hours = period.getDuration() / secondsForHour;
        final long minuts = (period.getDuration() - hours * secondsForHour) / secondsForMinut;
        final long seconds = period.getDuration() - secondsForHour * hours - secondsForMinut * minuts;

        return String.valueOf(hours + "h " + minuts + "m " + seconds + "s");
    }

    public String getFormattedTable() {
        if (period == null) return "";
        int secondsForHour = 3600;
        int secondsForMinut = 60;

        final long hours = period.getDuration() / secondsForHour;
        final long minuts = (period.getDuration() - hours * secondsForHour) / secondsForMinut;
        final long seconds = period.getDuration() - secondsForHour * hours - secondsForMinut * minuts;

        return String.valueOf(getName() + " ---> "
                + "duration = " + String.valueOf(hours + "h " + minuts + "m " + seconds + "s")) + " |"
                + "from: " + period.getStartWorkingDate() + " | "
                + "to: " + period.getFinalWorkingDate() + " |\n ";

    }

    @Override
    public int compareTo(@NonNull Object other) {
        String option = Settings.getInstance().getSortBy();
        int res = 0;
        switch (option) {
            case "name":
                res = this.name.compareToIgnoreCase(((Item) other).getName()); //case insensitive comparison
                break;
            case "type":
                res = this.type.compareToIgnoreCase(((Item) other).getType()); //case insensitive comparison
                break;
            case "date":
                Date thisDate = this.period.getFinalWorkingDate();
                Date otherDate = ((Item) other).getPeriod().getFinalWorkingDate();
                if (thisDate.before(otherDate)) {
                    res = 1;
                } else if (thisDate.after(otherDate)) {
                    res = -1;
                } else {
                    res = 0;
                }
                break;
        }
        return res;
    }
}

