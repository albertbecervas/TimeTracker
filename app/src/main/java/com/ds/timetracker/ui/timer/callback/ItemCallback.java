package com.ds.timetracker.ui.timer.callback;

import android.content.Intent;

import com.ds.timetracker.model.Project;

public interface ItemCallback {

    void onItemStateChanged();

    void onProjectItemSelected(int position);

    void onDeleteItem(int position);

    void onEditProject(int position, Project project);

    void onEditTask(int position, Intent intent);

}
