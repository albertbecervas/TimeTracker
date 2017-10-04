package com.ds.timetracker;

import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;

import java.util.ArrayList;

public class InitClass {

    InitClass() {
        Project project = new Project();
        project.setName("1");
        project.setDescription("vnfds");

        Task task = new Task();
        task.setName("firstTask");
        task.setDescription("vds");

        Task second = new Task();
        second.setName("secondTask");

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(second);

        project.setTasks(tasks);

        task.start();
        second.start();
    }
}
