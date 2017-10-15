package com.ds.timetracker.helpers;

import android.content.Context;
import android.provider.ContactsContract;

import com.ds.timetracker.callback.FirebaseCallback;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    private FirebaseCallback mProjectCallback;
    private DatabaseReference mDatabase;

    private ArrayList<Object> projectsList;

    public FirebaseHelper(Context context, DatabaseReference database) {
        mProjectCallback = (FirebaseCallback) context;
        mDatabase = database;
        projectsList = new ArrayList<>();
    }

    public FirebaseHelper(DatabaseReference database) {
        mDatabase = database;
        projectsList = new ArrayList<>();
    }

    public FirebaseHelper(){
    }

    public void getProjects() {

        mDatabase.child("/projects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dS : dataSnapshot.getChildren()) {

                    String item = dS.child("itemType").getValue(String.class);
                    if (item == null)return;
                    int itemType = Integer.parseInt(item);
                    String key = dS.getKey();
                    String name = dS.child("name").getValue(String.class);
                    String description = dS.child("description").getValue(String.class);

                    if (itemType == 0) {
                        String aux = mDatabase.child("projects").child(key).getRef().toString();
                        String[] reference = aux.split(".com/");
                        Project project = new Project(name, description, reference[1], key);
                        projectsList.add(project);
                    }

                    if (itemType == 1) {
                        Task task = new Task();
                        task.setName(name);
                        task.setKey(key);
                        task.setDescription(description);
                        String aux = mDatabase.child("projects").child(key).getRef().toString();
                        String[] reference = aux.split(".com/");
                        task.setDatabasePath(reference[1]);
                        projectsList.add(task);
                    }

                }

                mProjectCallback.onProjectsLoaded(projectsList);

                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getIntervals(){
        mDatabase.child("intervals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProject(String name, String description) {
        String key = mDatabase.child("projects").push().getKey();
        String itemType = "0";
        mDatabase.child("projects").child(key).child("itemType").setValue(itemType);
        mDatabase.child("projects").child(key).child("name").setValue(name);
        mDatabase.child("projects").child(key).child("description").setValue(description);
    }

    public void setTasks(String name, String description) {
        String key = mDatabase.child("projects").push().getKey();
        mDatabase.child("projects").child(key).child("name").setValue(name);
        mDatabase.child("projects").child(key).child("description").setValue(description);
        String itemType = "1";
        mDatabase.child("projects").child(key).child("itemType").setValue(itemType);
    }

    public void setInterval(Task task){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(task.getDatabasePath());
        databaseReference.child("intervals").setValue(task.getIntervals());
    }
}
