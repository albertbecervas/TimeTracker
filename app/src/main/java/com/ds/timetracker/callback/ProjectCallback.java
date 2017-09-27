package com.ds.timetracker.callback;

import com.ds.timetracker.model.Project;

import java.util.ArrayList;

public interface ProjectCallback {

    void onProjectsLoaded(ArrayList<Project> projects);

}
