package com.ds.timetracker.ui.timer.callback;

import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;

import java.util.ArrayList;

public interface FirebaseCallback {

    void onItemsLoaded(ArrayList<Item> items);

    void onIntervalLoaded(ArrayList<Interval> intervals);

}
