package com.ds.timetracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ds.timetracker.R;
import com.ds.timetracker.adapter.ViewTypeAdapter;
import com.ds.timetracker.callback.FirebaseCallback;
import com.ds.timetracker.callback.ItemCallback;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.observable.Clock;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * MainActivity is the class that shows us the projects arquitecture and containing tasks.
 * In the first level of the items tree it will display all items(projects and tasks) as if them were
 * pending from an invisible project.
 * <p>
 * In this activity we can also handle the tasks. When a task state is changed, the ItemCallback is called
 * from the containing adapter in order to set the array of active tasks that this tree level contains.
 * <p>
 * In order to know which is the level that is shown using the same activity, we are passing on intents
 * the reference that that project has on database. This is so optimized because we are only handling
 * with the data that this level contains which makes the application works faster.
 */
public class MainActivity extends AppCompatActivity implements Observer, FirebaseCallback, ItemCallback {

    private RecyclerView mRecyclerView; //RecyclerView used to display tasks and projects
    private ViewTypeAdapter mAdapter; //adapter that handles the items

    private String reference = "";//String that contains the reference (path) to the database.
    private DatabaseReference mDatabase; //database object used to upload/download data from Firebase
//    private FirebaseHelper mHelper; //helper class that has the methods to interact with server

    private CircularProgressView mProgressBar;//ProgressBar that is shown when app is loading data

    private ArrayList<String> activeTasks;//This ArrayList is used to know in every interval of time which items may be updated from the UI

    private ArrayList<Item> items;//This ArrayList is used to save all items that we must display on RecyclerView. This is setted from the server when FirebaseCallback is called

    private Boolean itemsToUpdate = false;//this is a door boolean used to know if we have new items to show on the screen or if the callback is being called every second to stop showing new items that are the same in fact.

    /**
     * onCreate function is the first one that is called when activity starts.
     * In this case, these are the very first things that we will do when app is started or when we
     * click on a project
     * <p>
     * We make some initializations of array variables in order to avoid the app crashing for
     * null pointer and check if we are coming from a project,
     * then we subscribe the class to the clock in order to recieve the information
     * every specified interval,
     * After that, we inflate the activity's views from the layout in order to make them interactable.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeTasks = new ArrayList<>();
        items = new ArrayList<>();

        if (getIntent().hasExtra("reference")) {
            reference = getIntent().getStringExtra("reference");
            mDatabase = FirebaseDatabase.getInstance().getReference(reference);
        }

        Clock.getInstance().addObserver(this);

        setViews();

        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clearAdapter();
        itemsToUpdate = true;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(reference);
//        mHelper = new FirebaseHelper(this, mDatabase);
//        mHelper.getItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemType = "";
        switch (item.getItemId()) {
            case R.id.add_project:
                itemType = "0";
                break;
            case R.id.add_task:
                itemType = "1";
                break;
        }
        Intent i = new Intent(this, CreateItemActivity.class);
        i.putExtra("reference", reference);
        i.putExtra("itemType", itemType);
        startActivity(i);
        return true;
    }

    private void setViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.startAnimation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_add));
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.projectsRV);
        FloatingActionButton addProject = findViewById(R.id.fab);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "I want to be a menu :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        ArrayList<Item> itemList = new ArrayList<>();
        mAdapter = new ViewTypeAdapter(this, itemList, mDatabase, getIntent().getBooleanExtra("isProject", false));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemStateChanged() {
        setItemsState();
    }

    private void setItemsState() {
//        int itemPosition = 0;
//        for (Item item : items) {
//            if (item.isStarted()) {
//                activeTasks.add(String.valueOf(itemPosition));
//            } else {
//                if (activeTasks.contains(String.valueOf(itemPosition)))
//                    activeTasks.remove(String.valueOf(itemPosition));
//            }
//            itemPosition++;
//        }
    }

    @Override
    public void update(Observable observable, Object o) {

//        for (final String task : activeTasks) {
//            Item item = items.get(Integer.valueOf(task));
//            if (item instanceof Task) {
//                if (!((Task) item).isLimited()) {
//                    ((Task) items.get(Integer.valueOf(task))).setFinalWorkingDate((Date) o);
//                    ((Task) items.get(Integer.valueOf(task))).updateInterval((Date) o);
//                } else {
//                    boolean max = ((Task) item).getIntervals().get(((Task) item).getIntervals().size() - 1).getDuration() >= ((Task) item).getMaxDuration();
//                    if (max) {
//                        activeTasks.remove(task);
//                        item.setStarted(false);
//                        ((Task) item).closeInterval((Date) o);
//                        ((Task) item).getIntervals().get(((Task) item).getIntervals().size() - 1).setOpen(false);
//                        mHelper.setInterval((Task) item);
//
//                    } else {
//                        ((Task) items.get(Integer.valueOf(task))).setFinalWorkingDate((Date) o);
//                        ((Task) items.get(Integer.valueOf(task))).updateInterval((Date) o);
//                    }
//                }
//            }
//            if (item instanceof Project) {
//                ((Project) items.get(Integer.valueOf(task))).setFinalWorkingDate((Date) o);
//            }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mAdapter.setItemsList(items);
//                    mAdapter.notifyItemChanged(Integer.valueOf(task));
//                }
//            });
//        }
    }

    //This following method will only be used in case we want to retrieve data from de server
    @Override
    public void onItemsLoaded(ArrayList<Item> items) {
        if (this.items.size() == items.size() && !itemsToUpdate) return;
        itemsToUpdate = false;
        this.items = items;
//        mAdapter.setItemsList(items);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.getItemAnimator().setChangeDuration(0);
        mProgressBar.setVisibility(View.GONE);
        setItemsState();
    }

    @Override
    public void onIntervalLoaded(ArrayList<Interval> intervals) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
