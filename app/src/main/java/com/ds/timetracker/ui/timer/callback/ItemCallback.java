package com.ds.timetracker.ui.timer.callback;

public interface ItemCallback {

    void onItemStateChanged();

    void onProjectItemSelected(int position);

    void onDeleteItem(int position);

    void onEditProject(int position);

}
