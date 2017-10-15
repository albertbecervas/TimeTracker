package com.ds.timetracker.helpers;

import android.content.Context;

import com.ds.timetracker.callback.FirebaseCallback;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    private FirebaseCallback mFirebaseCallback;
    private DatabaseReference mDatabase;

    private ArrayList<Object> itemsList;

    public FirebaseHelper(Context context, DatabaseReference database) {
        mFirebaseCallback = (FirebaseCallback) context;
        mDatabase = database;
        itemsList = new ArrayList<>();
    }

    public FirebaseHelper(DatabaseReference database) {
        mDatabase = database;
        itemsList = new ArrayList<>();
    }

    public FirebaseHelper() {
    }

    public void getItems() {

        mDatabase.child("/items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dS : dataSnapshot.getChildren()) {

                    String item = dS.child("itemType").getValue(String.class);
                    if (item == null) return;
                    int itemType = Integer.parseInt(item);
                    String key = dS.getKey();
                    String aux = mDatabase.child("items").child(key).getRef().toString();
                    String[] reference = aux.split(".com/");

                    if (itemType == 0) {
                        Project project = dS.getValue(Project.class);
                        if (project == null) return;
                        project.setDatabasePath(reference[1]);
                        itemsList.add(project);
                    }

                    if (itemType == 1) {
                        Task task = dS.getValue(Task.class);
                        if (task == null) return;
                        task.setDatabasePath(reference[1]);
                        itemsList.add(task);
                    }

                }

                mFirebaseCallback.onItemsLoaded(itemsList);

                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getIntervals() {
        mDatabase.child("intervals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Interval>> t = new GenericTypeIndicator<ArrayList<Interval>>() {
                };
                ArrayList<Interval> intervals = dataSnapshot.getValue(t);
                mFirebaseCallback.onIntervalLoaded(intervals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProject(Object project) {
        mDatabase.child("items").push().setValue(project);
    }

    public void setTasks(Task task) {
        mDatabase.child("items").push().setValue(task);
    }

    public void setInterval(Task task) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(task.getDatabasePath());
        databaseReference.child("durada").setValue(task.getDurada());
        databaseReference.child("intervals").setValue(task.getIntervals());
    }
}
