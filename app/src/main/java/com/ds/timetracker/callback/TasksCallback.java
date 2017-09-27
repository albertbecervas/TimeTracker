package com.ds.timetracker.callback;

import com.ds.timetracker.model.Task;

import java.util.ArrayList;

public interface TasksCallback {

   void onTasksLoaded(ArrayList<Task> tasks);

}
