package com.ds.timetracker.ui.timer.callback;

import android.content.Intent;

public interface ItemCallback {

    void onItemStateChanged();

    void onProjectItemSelected(int position);

    void onDeleteItem(int position);

    void onEditProject(int position);

    void onEditTask(int position, Intent intent);

}
