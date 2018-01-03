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

    /**
     * Saves the report in a file in order to have persistence in the application
     *
     * @param report report we want to save
     */
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

    /**
     * Gets the reports form the specified file and puts them on ArrayList
     *
     * @return ArrayList<Report>
     */
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


