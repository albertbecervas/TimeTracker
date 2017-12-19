package com.ds.timetracker.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.observable.Clock;
import com.ds.timetracker.ui.create.CreateItemActivity;
import com.ds.timetracker.ui.reports.ReportsActivity;
import com.ds.timetracker.ui.timer.adapter.ViewTypeAdapter;
import com.ds.timetracker.ui.timer.callback.ItemCallback;
import com.ds.timetracker.utils.Constants;
import com.ds.timetracker.utils.CustomFabMenu;
import com.ds.timetracker.utils.CustomFabMenuCallback;
import com.ds.timetracker.utils.ItemsTreeManager;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * ProjectDetailActivity is the class that shows us the projects arquitecture and containing tasks.
 * In the first level of the items tree it will display all items(projects and tasks) as if them were
 * pending from an invisible project.
 * <p>
 * In this activity we can also handle the tasks. When a ic_task state is changed, the ItemCallback is called
 * from the containing adapter in order to set the array of active tasks that this tree level contains.
 * <p>
 * In order to know which is the level that is shown using the same activity, we are passing on intents
 * the reference that that project has on database. This is so optimized because we are only handling
 * with the data that this level contains which makes the application works faster.
 */
public class ProjectDetailActivity extends AppCompatActivity implements Observer, ItemCallback, CustomFabMenuCallback {

    public static final String PROJECT_ITEMS = "project";
    private ItemsTreeManager itemsManager;

    private RecyclerView mRecyclerView; //RecyclerView used to display tasks and projects
    private ViewTypeAdapter mAdapter; //adapter that handles the items

    private ArrayList<Item> items;//This ArrayList is used to save all items that we must display on RecyclerView. This is setted from the server when FirebaseCallback is called
    private ArrayList<Item> treeLevelItems;
    private ArrayList<Integer> nodesReference;

    private ArrayList<Item> searchResults;

    private CircularProgressView mProgressBar;//ProgressBar that is shown when app is loading data
    private CustomFabMenu fabMenu;
    private MaterialSearchView searchView;

    private boolean isSearching = false;
    private String query = "";

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
        setContentView(R.layout.activity_project_detail);

        itemsManager = new ItemsTreeManager(this);

        Clock.getInstance().addObserver(this);

        nodesReference = new ArrayList<>();
        treeLevelItems = new ArrayList<>();
        searchResults = new ArrayList<>();

        setViews();
        setAdapter();
    }


    @Override
    protected void onResume() {
        super.onResume();

        items = itemsManager.getItems();
        treeLevelItems = items;

        for (Integer position : nodesReference) {
            treeLevelItems = ((Project) treeLevelItems.get(position)).getItems();
        }

        mAdapter.clearAdapter();
        mAdapter.setItemsList(treeLevelItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                itemsManager.resetItems();
                items = itemsManager.getItems();
                mAdapter.setItemsList(treeLevelItems);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.report:
                startActivity(new Intent(this, ReportsActivity.class));
                finish();
                break;
        }
        return true;
    }

    private void setViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                isSearching = true;
                query += newText;
                if (query.length() == 0) {
                    searchResults = new ArrayList<>();
                    query = "";
                    isSearching = false;
                    mAdapter.setItemsList(treeLevelItems);
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (Item item : treeLevelItems) {
                        if (item.getName().startsWith(query)) {
                            searchResults.add(item);
                        }
                    }
                    mAdapter.clearAdapter();
                    mAdapter.setItemsList(searchResults);
                    mAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.startAnimation();

        fabMenu = findViewById(R.id.fabMenu);

        mRecyclerView = findViewById(R.id.projectsRV);
    }

    private void setAdapter() {
        mAdapter = new ViewTypeAdapter(this, treeLevelItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemStateChanged() {
        itemsManager.saveItems(items);
    }

    @Override
    public void onProjectItemSelected(int adapterPosition) {

        nodesReference.add(adapterPosition);

        treeLevelItems = items;
        for (Integer position : nodesReference) {
            treeLevelItems = ((Project) treeLevelItems.get(position)).getItems();
        }

        mAdapter.setItemsList(treeLevelItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteItem(int position) {
        treeLevelItems.remove(position);
    }

    @Override
    public void update(Observable observable, Object o) {

        if (!isSearching) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.clearAdapter();
                    mAdapter.setItemsList(treeLevelItems);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fabMenu.closeFABMenu();
        itemsManager.saveItems(items);
    }

    @Override
    public void onBackPressed() {
        fabMenu.closeFABMenu();

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }

        if (isSearching) {
            isSearching = false;
            query = "";
            searchResults = new ArrayList<>();
            mAdapter.setItemsList(treeLevelItems);
            mAdapter.notifyDataSetChanged();
            return;
        }

        if (nodesReference.size() > 0) {
            nodesReference.remove(nodesReference.size() - 1);
            treeLevelItems = items;
            for (Integer position : nodesReference) {
                treeLevelItems = ((Project) treeLevelItems.get(position)).getItems();
            }
            mAdapter.setItemsList(treeLevelItems);
            mAdapter.notifyDataSetChanged();
        } else super.onBackPressed();
    }


    @Override
    public void onCreateItemSelected(String type) {
        Intent taskIntent;
        taskIntent = new Intent(this, CreateItemActivity.class);
        taskIntent.putIntegerArrayListExtra("nodesReference", nodesReference);

        if (type.equals(Constants.PROJECT)) {
            taskIntent.putExtra("itemType", Constants.PROJECT);
        } else {
            taskIntent.putExtra("itemType", Constants.TASK);
        }

        startActivity(taskIntent);
    }
}
