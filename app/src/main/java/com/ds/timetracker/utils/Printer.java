package com.ds.timetracker.utils;

import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.observable.Clock;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * Prints the items tree in a formatted table every period of time.
 */
public class Printer implements Observer {


    private ArrayList<Item> items;

    public Printer(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Prints the items table in console in a formatted way
     */
    public void printTable() {
        Clock.getInstance().addObserver(this); //We need the clock in order to repeat the print of the table periodically
        print("\r \n\n TIME TABLE:\n");
        for (Item item : items) {
            recursivePrint(item, "");
        }
    }

    /**
     * Goes through all tree in order to print every item inside it
     *
     * @param item
     * @param tabs
     */
    public void recursivePrint(Item item, String tabs) {
        if (item instanceof Task) {
            print(tabs + item.getFormattedTable());
        } else {
            print(tabs + "-" + item.getFormattedTable());
            tabs = tabs + "   "; //leaves some space in order to see in which item level are we
            for (Item subItem : ((Project) item).getItems()) {
                recursivePrint(subItem, tabs);
            }
        }
    }

    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void update(Observable o, Object arg) {
        printTable();
    }

}