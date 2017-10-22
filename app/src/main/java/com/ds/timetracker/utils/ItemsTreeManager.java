package com.ds.timetracker.utils;

import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class ItemsTreeManager {

    private static File file;

    public static ArrayList<Item> setTree() {
        ArrayList<Item> items = new ArrayList<Item>();
        Project project = new Project("P1", "first project from node 0", null);
        project.newProject("P2", "subproject of P1");
        project.newTask("T3", "task 3 in project P1");

        ((Project) project.getItems().get(0)).newTask("T1", "sub sub task1 from P2");
        ((Project) project.getItems().get(0)).newTask("T2", "sub sub task2 from P2");

        items.add(project);

        return items;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Item> getItems() {
        file = new File("timetracker.dat");

        ArrayList<Item> items = new ArrayList<>();

        if (file.exists()) {
            // read object from file
            try {
                FileInputStream fips = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fips);
                items = (ArrayList<Item>) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return items;
    }

    public static void saveItems(ArrayList<Item> items) {

        // write object to file
        try {
            FileOutputStream fops = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fops);
            out.writeObject(items);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void resetItems() {
        if (file.exists()) {
            file.delete();
        }
    }

}
