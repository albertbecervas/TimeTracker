package com.ds.timetracker.callback;

import com.ds.timetracker.model.Interval;

import java.util.ArrayList;

public interface FirebaseCallback {

    void onItemsLoaded(ArrayList<Object> items);

    void onIntervalLoaded(ArrayList<Interval> intervals);

}
