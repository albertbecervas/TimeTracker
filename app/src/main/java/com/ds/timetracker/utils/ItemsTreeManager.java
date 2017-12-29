package com.ds.timetracker.utils;


import android.content.Context;

import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.reports.builders.Report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class is used to manage the persistence of the items tree
 */
public class ItemsTreeManager {

    private static String fileName = "itemsTree.dat"; //name of the file where the items tree is saved
    private static String reportsFileName = "reports.dat";

    private Context context;

    public ItemsTreeManager(Context context){
        this.context = context;
    }

    /**
     * Sets the basic given items tree in order to run the tests
     *
     * @return ArrayList<Item>
     */
    public static ArrayList<Item> setTree() {
        ArrayList<Item> items = new ArrayList<Item>();
//
//        Project project = new Project("P1", "first project from node 0", null);
//        project.newProject("P2", "subproject of P1");
//        project.newTask("T3", "task 3 in project P1");
//
//        //in order to set a task into a project that is inside another project, we have to iterate within it.
//        ((Project) project.getItems().get(0)).newTask("T1", "sub sub task1 from P2");
//        ((Project) project.getItems().get(0)).newTask("T2", "sub sub task2 from P2");
//
//        //once we have all the project items set, we add the project on the main list
//        items.add(project);

        return items;
    }

    public static ArrayList<Item> setTreeOfFita2() {
        ArrayList<Item> items = new ArrayList<Item>();

//        Project p1 = new Project("P1", "p1", null);
//        p1.newProject("P1.2", " subproject from p1");
//        p1.newTask("T1", "task from p1");
//        p1.newTask("T2", "task from p1");
//
//        ((Project) p1.getItems().get(0)).newTask("T4", "task from p1.2");
//
//        Project p2 = new Project("P2", "p2", null);
//        p2.newTask("T3", "task from p2");

//        items.add(p1);
//        items.add(p2);
        return items;
    }

    /**
     * Gets the items form the specified file and puts them on ArrayList
     *
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getItems() {
        File file = new File(context.getFilesDir() +"/"+ fileName);

        ArrayList<Item> items = new ArrayList<>();

        //if the file doesn't exists this function will return an empty array
        if (file.exists()) {
            // read object from file
            try {
                FileInputStream fips = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fips);
                items = (ArrayList<Item>) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                items = new ArrayList<>();
            }
        }
        return items;
    }

    /**
     * Saves the items tree in a file in order to have persistence in the application
     *
     * @param items
     */
    public void saveItems(ArrayList<Item> items) {

        File file = new File(context.getFilesDir() +"/"+ fileName);

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

    /**
     * Deletes the file where the tree is saved
     */
    public void resetItems() {
        File file = new File(context.getFilesDir() + "/" +fileName);
        if (file.exists()) {
            file.delete();
        }
        File file1 = new File(context.getFilesDir() + "/" + reportsFileName);
        if (file1.exists()) {
            file1.delete();
        }
    }

    public void saveReport(Report report){

        ArrayList<Report> reports = getReports();
        reports.add(report);

        File file = new File(context.getFilesDir() +"/"+ reportsFileName);

        // write object to file
        try {
            FileOutputStream fops = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fops);
            out.writeObject(reports);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ArrayList<Report> getReports(){
        File file = new File(context.getFilesDir() +"/"+ reportsFileName);

        ArrayList<Report> reports = new ArrayList<>();

        //if the file doesn't exists this function will return an empty array
        if (file.exists()) {
            // read object from file
            try {
                FileInputStream fips = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fips);
                reports = (ArrayList<Report>) in.readObject();
                in.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                reports = new ArrayList<>();
            }
        }
        return reports;
    }
}


