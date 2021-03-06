package com.ds.timetracker.ui.reports.builders;

import java.io.Serializable;

/**
 * Helper class in order to keep the important data that we want to show on report
 */
public class ItemReportDetail implements Serializable {

    String name;
    String duration;
    String initialDate;
    String finalDate;

    int secondsForHour = 3600;
    int secondsForMinut = 60;

    public ItemReportDetail(String name, long duration, String initial, String finalDate) {
        this.name = name;
        this.initialDate = initial;
        this.finalDate = finalDate;

        final long hours = duration / secondsForHour;
        final long minuts = (duration - hours * secondsForHour) / secondsForMinut;
        final long seconds = duration - secondsForHour * hours - secondsForMinut * minuts;
        this.duration = String.valueOf(hours + "h " + minuts + "m " + seconds + "s");
    }

    public String getIncludedDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }
}
