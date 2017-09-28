package com.ds.timetracker.helpers;

import android.content.Context;

import com.ds.timetracker.callback.TasksCallback;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TasksHelper {

    private TasksCallback mCallback;

    private DatabaseReference mDatabase;

    private ArrayList<Task> tasksList;

    private String mProjectID;

    public TasksHelper(Context context, DatabaseReference databaseReference, String projectID) {
        this.mProjectID = projectID;
        mCallback = (TasksCallback) context;
        mDatabase = databaseReference;
        tasksList = new ArrayList<>();
    }

    public TasksHelper(DatabaseReference databaseReference, String projectID){
        mDatabase = databaseReference;
        this.mProjectID = projectID;
        tasksList = new ArrayList<>();
    }

    public void getTasks(){
        mDatabase.child("Projects").child(mProjectID).child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dS : dataSnapshot.getChildren()) {

                    String key = dS.getKey();
                    String name = dS.child("name").getValue(String.class);
                    String description = dS.child("description").getValue(String.class);
                    Task task = new Task();
                    task.setName(name);
                    task.setKey(key);
                    task.setDescription(description);

                    tasksList.add(task);
                }

                mCallback.onTasksLoaded(tasksList);

                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setTasks(String name, String description){
        String key = mDatabase.child("Projects").child(mProjectID).child("Tasks").push().getKey();
        mDatabase.child("Projects").child(mProjectID).child("Tasks").child(key).child("name").setValue(name);
        mDatabase.child("Projects").child(mProjectID).child("Tasks").child(key).child("description").setValue(description);
    }
}
