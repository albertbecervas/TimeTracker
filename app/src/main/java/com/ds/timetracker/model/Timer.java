package com.ds.timetracker.model;

import android.os.SystemClock;

import com.ds.timetracker.callback.TimeCallback;

import java.sql.Time;

public class Timer {

    private TimeCallback timeCallback;

    Timer() {
        Task task = new Task();
        timeCallback = task;

        for (int i = 0; i < 10; i++) {
            SystemClock.sleep(1000);
            timeCallback.newSecond(i);
        }
    }
}
