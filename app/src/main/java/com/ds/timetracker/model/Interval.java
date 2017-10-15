package com.ds.timetracker.model;

import java.io.Serializable;
import java.util.Date;

public class Interval implements Serializable {

    private Date startWorkingLogDate;
    private Date endWorkingLogDate;

    public Interval(Date startWorkingLogDate, Date endWorkingLogDate) {
        this.startWorkingLogDate = startWorkingLogDate;
        this.endWorkingLogDate = endWorkingLogDate;
    }

    public Date getStartWorkingLogDate() {
        return startWorkingLogDate;
    }

    public void setStartWorkingLogDate(Date startWorkingLogDate) {
        this.startWorkingLogDate = startWorkingLogDate;
    }

    public Date getEndWorkingLogDate() {
        return endWorkingLogDate;
    }

    public void setEndWorkingLogDate(Date endWorkingLogDate) {
        this.endWorkingLogDate = endWorkingLogDate;
    }
}
