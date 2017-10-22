package com.ds.timetracker;

import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.utils.ItemsTreeManager;
import com.ds.timetracker.utils.Printer;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static final int RUN_TEST_1 = 1;
    private static final int RUN_TEST_2 = 2;
    private static final int RESET_TREE = 3;
    private static final int CREATE_PROJECT = 4;
    private static final int CREATE_TASK = 5;

    private static ArrayList<Item> items;

    public Main(){
        main(null);
    }

    public static void main(String[] args) {
        //set the file where the items are saved

        //We ask the file to return saved items
        items = ItemsTreeManager.getItems();

        //if we don't get any items from file, we set the a new empty tree and then we save it in the file
        if (items.isEmpty()) {
            items = ItemsTreeManager.setTree();
            ItemsTreeManager.saveItems(items);
        }

        //once we have loaded the list of main items we show a menu to the user and ask for an action
        showMenu();

    }

    private static void showMenu() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        System.out.println("Select an action:");
        System.out.println("  1.Run task test");
        System.out.println("  2.Run simultaneous tasks test");
        System.out.println("  3.Reset tree");
        System.out.println("  4.Create task");
        System.out.println("  5.Create project");
        System.out.println("Enter a number: ");

//        int menuOption = reader.nextInt(); // Scans the next token of the input as an int.

        setMenuAction(1);

        reader.close();
    }

    private static void setMenuAction(int menuOption) {
        //once finished
        switch (menuOption) {
            case RUN_TEST_1:
                //Using the class Printer in order to print the table every specified time
                new Printer(items);
                simulateUserInteraction1();
                break;
            case RUN_TEST_2:
                //Using the class Printer in order to print the table every specified time
                new Printer(items);
                simulateUserInteraction2();
                break;
            case RESET_TREE:
                ItemsTreeManager.resetItems();
                showMenu();
                break;
            case CREATE_PROJECT:
                break;
            case CREATE_TASK:
                break;
            default:
                //Using the class Printer in order to print the table every specified time
                new Printer(items);
                simulateUserInteraction1();
                break;
        }
    }

    //Test1
    private static void simulateUserInteraction1() {

        //starting the task T3 in project P1
        ((Project) items.get(0)).startTask(1);

        //waiting for three seconds before pausing T3
        sleep(3000);

        //pause the task T3 in project P1
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

        //here we may have table 1.

        //wait for 7s until next task start
        sleep(7000);

        //start the task T2 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).startTask(1);

        //wait for 10 seconds before pausing T2
        sleep(10500);

        //pause the task T2 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

        //start T3 again
        ((Project) items.get(0)).startTask(1);

        //wait 2s before pausing T3
        sleep(3000);

        //pause T3
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

    }

    //Test2
    private static void simulateUserInteraction2() {
        //starting the task T3 in project P1
        ((Project) items.get(0)).startTask(1);

        //waiting for 4s before starting T2
        sleep(4100);

        //start the task T2 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).startTask(1);
        ItemsTreeManager.saveItems(items);

        //waiting for 2s before pausing T3
        sleep(2100);

        //pause the task T3 in project P1
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

        //waiting for 2s before starting T1
        sleep(2100);

        //start the task T1 in subproject P2
        ((Project) ((Project) items.get(0)).getItems().get(0)).startTask(0);

        //wait for 4s before pausing T1
        sleep(4100);

        //pause the task T1
        ((Project) ((Project) items.get(0)).getItems().get(0)).stopTask(0);
        ItemsTreeManager.saveItems(items);

        //wait for 2s before pausing T2
        sleep(2100);

        //pause the task T2
        ((Project) ((Project) items.get(0)).getItems().get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

        //wait for 4s before starting T3 again
        sleep(4100);

        //starting T3 again
        ((Project) items.get(0)).startTask(1);

        //waiting for 2s before pausing T3
        sleep(2100);

        //pausing T3 and finishing the test
        ((Project) items.get(0)).stopTask(1);
        ItemsTreeManager.saveItems(items);

    }


    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

