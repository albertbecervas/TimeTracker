package com.ds.timetracker.model;

import com.ds.timetracker.callback.TimeCallback;

import java.util.Date;
import java.util.TimerTask;

public class Timer {

    private static Timer instance = null;

    public Timer(TimeCallback callback) {
        TimeCallback timeCallback = callback;
        Thread thread = new Thread(timeCallback);
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(thread, new Date(), 1000);
    }

    public Timer getInstance(TimeCallback callback){
        if (instance == null) return new Timer(callback);
        return instance;
    }

    static class Thread extends TimerTask {

        private TimeCallback timeCallback;

        Thread(TimeCallback timeCallback) {
            this.timeCallback = timeCallback;
        }

        @Override
        public void run() {
            timeCallback.newSecond(new Date());
        }
    }
}

