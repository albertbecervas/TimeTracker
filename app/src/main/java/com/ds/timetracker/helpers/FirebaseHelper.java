//package com.ds.timetracker.helpers;
//
//import android.content.Context;
//
//import com.ds.timetracker.callback.FirebaseCallback;
//import com.ds.timetracker.model.Item;
//import com.ds.timetracker.model.Project;
//import com.ds.timetracker.model.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.GenericTypeIndicator;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//public class FirebaseHelper {
//
//    private FirebaseCallback mFirebaseCallback;
//    private DatabaseReference mDatabase;
//
//    private ArrayList<Item> itemsList;
//
//    public FirebaseHelper(Context context, DatabaseReference database) {
//        mFirebaseCallback = (FirebaseCallback) context;
//        mDatabase = database;
//        itemsList = new ArrayList<>();
//    }
//
//    public FirebaseHelper(DatabaseReference database) {
//        mDatabase = database;
//        itemsList = new ArrayList<>();
//    }
//
//    public void getItems() {
//
//        mDatabase.child("/items").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot dS : dataSnapshot.getChildren()) {
//
//                    String item = dS.child("itemType").getValue(String.class);
//                    if (item == null) return;
//                    int itemType = Integer.parseInt(item);
//                    String key = dS.getKey();
//                    String aux = mDatabase.child("items").child(key).getRef().toString();
//                    String[] reference = aux.split(".com/");
//
////                    ArrayList<Item> items = new ArrayList<>();
////                    for (DataSnapshot d : dS.child("items").getChildren()){
////                        items.add(d.getValue(Item.class));
////                    }
//
//                    if (itemType == 0) {
//
//                        Project project = dS.getValue(Project.class);
//                        if (project == null) return;
////                        project.setDatabasePath(reference[1]);
////                        project.setItems(items);
//                        itemsList.add(project);
//                    }
//
//                    if (itemType == 1) {
//                        Task task = dS.getValue(Task.class);
//                        if (task == null) return;
////                        task.setDatabasePath(reference[1]);
//                        itemsList.add(task);
//                    }
//
//                }
//
//                mFirebaseCallback.onItemsLoaded(itemsList);
//
//                mDatabase.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public void getIntervals() {
//        mDatabase.child("intervals").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<ArrayList<Interval>> t = new GenericTypeIndicator<ArrayList<Interval>>() {
//                };
//                ArrayList<Interval> intervals = dataSnapshot.getValue(t);
//                mFirebaseCallback.onIntervalLoaded(intervals);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public void setItem(Item item) {
//        mDatabase.child("items").push().setValue(item);
//    }
//
//    public void setInterval(Task task) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(task.getDatabasePath());
//        databaseReference.child("durada").setValue(task.getDurada());
//        databaseReference.child("intervals").setValue(task.getIntervals());
//        databaseReference.child("started").setValue(task.isStarted());
//    }
//
//    public void setProjectStarted() {
//        mDatabase.child("started").setValue(true);
//        mDatabase.child("initialWorkingDate").setValue(new Date());
//        mDatabase.child("finalWorkingDate").setValue(new Date());
//    }
//
//    public void setProjectStopped() {
//        mDatabase.child("started").setValue(false);
//        mDatabase.child("finalWorkingDate").setValue(new Date());
////        mDatabase.child("duration").setValue(project.getDuration());
//    }
//
//    public void setTaskStarted(Task task) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(task.getDatabasePath());
//        reference.child("started").setValue(task.isStarted());
//        reference.child("intervals").setValue(task.getIntervals());
//
//    }
//
//}
