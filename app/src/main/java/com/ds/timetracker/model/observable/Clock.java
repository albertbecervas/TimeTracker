package com.ds.timetracker.model.observable;


import java.util.*;

public class Clock extends Observable {

    public Clock() {
        Observable observer = this;
        Thread thread = new Thread((Clock) observer);
        Timer timer = new Timer();
        timer.schedule(thread, new Date(), 1000);
    }


    class Thread extends TimerTask {

        Clock mObservable;

        Thread(Clock observable) {
            mObservable = observable;
        }

        @Override
        public void run() {
            mObservable.setChanged();
            mObservable.notifyObservers(new Date());
        }
    }

}
