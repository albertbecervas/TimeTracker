package com.ds.timetracker.helpers;

import android.content.Context;

import com.ds.timetracker.callback.ProjectCallback;
import com.ds.timetracker.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectHelper {

    private ProjectCallback mProjectCallback;
    private DatabaseReference mDatabase;

    private ArrayList<Project> projectsList;

    public ProjectHelper(Context context, DatabaseReference database) {
        mProjectCallback = (ProjectCallback) context;
        mDatabase = database;
        projectsList = new ArrayList<>();
    }

    public ProjectHelper(DatabaseReference database){
        mDatabase = database;
        projectsList = new ArrayList<>();
    }

    public void getProjects() {

        mDatabase.child("Projects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dS : dataSnapshot.getChildren()) {

                    String key = dS.getKey();
                    String name = dS.child("name").getValue(String.class);
                    String description = dS.child("description").getValue(String.class);
                    Project project = new Project();
                    project.setName(name);
                    project.setKey(key);
                    project.setDescription(description);

                    projectsList.add(project);
                }

                mProjectCallback.onProjectsLoaded(projectsList);

                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProject(String name, String description){
        String key = mDatabase.child("Projects").push().getKey();
        mDatabase.child("Projects").child(key).child("name").setValue(name);
        mDatabase.child("Projects").child(key).child("description").setValue(description);
    }
}
