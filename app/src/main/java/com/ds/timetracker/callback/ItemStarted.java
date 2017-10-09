package com.ds.timetracker.callback;

import com.ds.timetracker.adapter.viewholder.TaskViewHolder;
import com.ds.timetracker.model.Task;

public interface ItemStarted {

    void onItemStarted(String name,Integer position, Task task, TaskViewHolder holder);

    void onItemRemoved();

}
