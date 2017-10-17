package com.ds.timetracker.ui;

import android.app.PendingIntent;
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
import com.ds.timetracker.callback.ItemStarted;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.helpers.Temporitzador;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.observable.Clock;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer, FirebaseCallback, ItemStarted {

    private RecyclerView mRecyclerView;
    private ViewTypeAdapter mAdapter;

    private DatabaseReference mDatabase;

    private CircularProgressView mProgressBar;

    private ArrayList<String> activeTasks;
    private ArrayList<Item> items;

    private String reference = "";

    private Boolean itemsToUpdate = false;

    private Boolean isProject = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentoLanzar = new Intent(getBaseContext(), Temporitzador.class);
        PendingIntent pIntent=PendingIntent.getBroadcast(this, 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);


        activeTasks = new ArrayList<>();
        items = new ArrayList<>();

        Clock.getInstance().addObserver(this);

        setViews();

        if (getIntent().hasExtra("reference")) {
            reference = getIntent().getStringExtra("reference");
            mDatabase = FirebaseDatabase.getInstance().getReference(reference);

            isProject = getIntent().getBooleanExtra("isProject", false);
        }
        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clearAdapter();
        itemsToUpdate = true;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(reference);
        new FirebaseHelper(this, mDatabase).getItems();
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
        mAdapter = new ViewTypeAdapter(this, itemList, mDatabase, isProject);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemStateChanged() {
        setItemsState();
    }

    private void setItemsState() {
        int itemPosition = 0;
        for (Item item : items) {
            if (item.isStarted()) {
                activeTasks.add(String.valueOf(itemPosition));
            } else {
                if (activeTasks.contains(String.valueOf(itemPosition)))
                    activeTasks.remove(String.valueOf(itemPosition));
            }
            itemPosition++;
        }
    }

    @Override
    public void update(Observable observable, Object o) {

        for (final String task : activeTasks) {
            Item item = items.get(Integer.valueOf(task));
            if (item instanceof Task) {
                ((Task) items.get(Integer.valueOf(task))).setFinalWorkingDate((Date) o);
                ((Task) items.get(Integer.valueOf(task))).updateInterval((Date) o);
            }
            if (item instanceof Project) {
                ((Project) items.get(Integer.valueOf(task))).setFinalWorkingDate((Date) o);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setItemsList(items);
                    mAdapter.notifyItemChanged(Integer.valueOf(task));
                }
            });
        }
    }

    //This following method will only be used in case we want to retrieve data from de server
    @Override
    public void onItemsLoaded(ArrayList<Item> items) {
        if (this.items.size() == items.size() && !itemsToUpdate) return;
        itemsToUpdate = false;
        this.items = items;
        mAdapter.setItemsList(items);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.getItemAnimator().setChangeDuration(0);
        mProgressBar.setVisibility(View.GONE);
        setItemsState();
    }

    @Override
    public void onIntervalLoaded(ArrayList<Interval> intervals) {

    }
}
