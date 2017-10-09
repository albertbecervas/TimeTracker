package com.ds.timetracker.model;

public class Interval {

    private String startWorkingLogDate;
    private String endWorkingLogDate;

    public Interval(String startWorkingLogDate) {
        this.startWorkingLogDate = startWorkingLogDate;
    }

    public String getStartWorkingLogDate() {
        return startWorkingLogDate;
    }

    public void setStartWorkingLogDate(String startWorkingLogDate) {
        this.startWorkingLogDate = startWorkingLogDate;
    }

    public String getEndWorkingLogDate() {
        return endWorkingLogDate;
    }

    public void setEndWorkingLogDate(String endWorkingLogDate) {
        this.endWorkingLogDate = endWorkingLogDate;
    }
}
