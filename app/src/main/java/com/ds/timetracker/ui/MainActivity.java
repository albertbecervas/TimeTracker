package com.ds.timetracker.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Settings;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.observable.Clock;
import com.ds.timetracker.ui.create.CreateProjectActivity;
import com.ds.timetracker.ui.create.CreateReportActivity;
import com.ds.timetracker.ui.create.CreateTaskActivity;
import com.ds.timetracker.ui.reports.ReportFragment;
import com.ds.timetracker.ui.reports.builders.Report;
import com.ds.timetracker.ui.settings.SettingsActivity;
import com.ds.timetracker.ui.timer.ProjectFragment;
import com.ds.timetracker.utils.AppSharedPreferences;
import com.ds.timetracker.utils.CustomFabMenu;
import com.ds.timetracker.utils.CustomFabMenuCallback;
import com.ds.timetracker.utils.ItemsTreeManager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * In this activity we will have the main of the App.
 * We have all items displayed and all reports.
 */
public class MainActivity extends AppCompatActivity implements Observer, CustomFabMenuCallback {

    private final static int CREATE_REPORT_RESULT = 0;
    private final static int CREATE_ITEM_RESULT = 1;
    public final static int EDIT_ITEM_RESULT = 2;
    private final static int MY_PERMISSIONS_REQUEST_READ_WRITE = 3;

    public ItemsTreeManager itemsTreeManager;
    public AppSharedPreferences mPrefs;

    private ProjectFragment projectFragment;//fragment where all items will be displayed
    private ReportFragment reportFragment;//fragment where all reports will be displayed

    private CustomFabMenu fabMenu;
    private MaterialSearchView searchView;
    private TabLayout tabLayout;
    private ConstraintLayout constraintLayout;

    public ArrayList<Report> reports;

    public Project father;
    public ArrayList<Item> items;//entire tree of items
    public ArrayList<Item> treeLevelItems;//lists of items that father project has
    public ArrayList<Integer> nodesReference;//lists of reference of searched nodes positions

    private boolean isSearching = false;//control boolean in order not to update RecyclerView

    public String path = "";//path of projects travelled

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = AppSharedPreferences.getInstance(this);
        setLocale(new Locale(mPrefs.getLocale()));

        Settings.getInstance().setSortBy(mPrefs.getSortBy());
        Settings.getInstance().setClockSeconds(mPrefs.getClockSeconds());

        setContentView(R.layout.activity_main);

        //In order to have the permission to store data on internal Storage
        requestPermissions();

        itemsTreeManager = new ItemsTreeManager(this);

        projectFragment = ProjectFragment.newInstance();
        reportFragment = ReportFragment.newInstance();

        items = itemsTreeManager.getItems();
        reports = itemsTreeManager.getReports();
        nodesReference = new ArrayList<>();

        //Starts the viewHolder fragments
        setTabs();

        Clock.getInstance().addObserver(this);

        setViews();

    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                Toast.makeText(this, "you need to accept", Toast.LENGTH_LONG).show();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_WRITE);
            }
        }
    }

    private void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void setTabs() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(projectFragment, getString(R.string.projectsFragment));
        adapter.addFragment(reportFragment, getString(R.string.reportsFragment));
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
                if (!newText.equals("")) {
                    ArrayList<Item> searchResults = new ArrayList<>();
                    for (Item item : items) {
                        if (item.getName().startsWith(newText)) {
                            searchResults.add(item);
                        }
                    }
                    projectFragment.setSearchResults(searchResults);
                } else {
                    projectFragment.setSearchResults(new ArrayList<Item>());
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                tabLayout.setVisibility(View.GONE);
                isSearching = true;
            }

            @Override
            public void onSearchViewClosed() {
                tabLayout.setVisibility(View.VISIBLE);
                isSearching = false;
            }
        });

        fabMenu = findViewById(R.id.fabMenu);
        constraintLayout = findViewById(R.id.constraintLayout);
    }

    @Override
    protected void onResume() {
        treeLevelItems = items;
        for (Integer position : nodesReference) {
            father = (Project) treeLevelItems.get(position);
            treeLevelItems = ((Project) treeLevelItems.get(position)).getItems();
            path += "/" + father.getName();
        }
        projectFragment.setItems(treeLevelItems);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fabMenu.closeFABMenu();
        itemsTreeManager.saveItems(items);
    }

    @Override
    public void onBackPressed() {
        fabMenu.closeFABMenu();

        path = "";//reset path

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return;
        }

        //if we will go to main level we set the back button visibility to gone.
        if (nodesReference.size() == 1) {
            projectFragment.setBackButtonVisible(View.GONE);
        }

        //if we are not in the first level of the tree we show a level before
        //else we alert the user that all tasks will be stopped.
        if (nodesReference.size() > 0) {
            nodesReference.remove(nodesReference.size() - 1);
            treeLevelItems = items;
            for (Integer position : nodesReference) {
                father = (Project) treeLevelItems.get(position);
                treeLevelItems = ((Project) treeLevelItems.get(position)).getItems();
                path += "/" + father.getName();
            }
            if (nodesReference.size() == 0) father = null;
            projectFragment.setItems(treeLevelItems);
        } else {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, R.string.exitMessage, Snackbar.LENGTH_LONG)
                    .setAction(R.string.exitButton, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.super.onBackPressed();
                        }
                    });
            snackbar.show();
        }
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
                Clock.getInstance().deleteObserver(this);
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
            case R.id.delete:
                deleteItems();
                break;
            case R.id.stop:
                if (item.getTitle().equals(getString(R.string.pause_all))) {
                    for (Item item1 : items) {
                        recursiveTreeSearchToPause(item1);
                    }
                    treeLevelItems = items;
                    projectFragment.setItems(items);
                    item.setTitle(R.string.start_all);
                } else {
                    for (Item item1 : items) {
                        recursiveTreeSearchToStart(item1);
                    }
                    treeLevelItems = items;
                    projectFragment.setItems(items);
                    item.setTitle(R.string.pause_all);
                }
                break;
            case R.id.sortByDate:
                mPrefs.setSortBy("date");
                Settings.getInstance().setSortBy("date");
                projectFragment.setItems(treeLevelItems);
                break;
            case R.id.sortByName:
                mPrefs.setSortBy("name");
                Settings.getInstance().setSortBy("name");
                projectFragment.setItems(treeLevelItems);
                break;
            case R.id.sortByTypes:
                mPrefs.setSortBy("type");
                Settings.getInstance().setSortBy("type");
                projectFragment.setItems(treeLevelItems);
                break;
        }
        return true;
    }

    private void deleteItems() {
        if (nodesReference.size() == 0) {
            itemsTreeManager.resetItems();
        } else {
            father.deleteItems();
            itemsTreeManager.saveItems(items);
        }
        items = itemsTreeManager.getItems();
        reports = itemsTreeManager.getReports();
        treeLevelItems = items;
        projectFragment.setItems(treeLevelItems);
        reportFragment.setReports(reports);
    }

    /**
     * iterates all tree and stops every task that is started
     *
     * @param item that we want to stop
     */
    private void recursiveTreeSearchToPause(Item item) {
        if (item instanceof Task) {//Basic case
            if (item.isOpen()) {
                ((Task) item).stop();
                ((Task) item).setPausedWithAll(true);
            }

        } else {
            for (Item subItem : ((Project) item).getItems()) {
                recursiveTreeSearchToPause(subItem); //recursive function
            }
        }
    }

    /**
     * iterates all tree and starts every task that we have stopped
     *
     * @param item that we want to start
     */
    private void recursiveTreeSearchToStart(Item item) {
        if (item instanceof Task) {//Basic case
            if (((Task) item).isPausedWithAll()) {
                ((Task) item).start();
                ((Task) item).setPausedWithAll(false);
            }
        } else {
            for (Item subItem : ((Project) item).getItems()) {
                recursiveTreeSearchToPause(subItem); //recursive function
            }
        }
    }

    //Callback from FAB
    @Override
    public void onCreateItemSelected(String type) {
        Intent i = null;
        int requestCode = CREATE_ITEM_RESULT;

        switch (type) {
            case "task":
                i = new Intent(this, CreateTaskActivity.class);
                i.putIntegerArrayListExtra("nodesReference", nodesReference);
                break;
            case "project":
                i = new Intent(this, CreateProjectActivity.class);
                i.putIntegerArrayListExtra("nodesReference", nodesReference);
                break;
            case "report":
                i = new Intent(this, CreateReportActivity.class);
                requestCode = CREATE_REPORT_RESULT;
                break;
        }

        startActivityForResult(i, requestCode);
    }


    //Clock update
    @Override
    public void update(Observable observable, Object o) {
        if (projectFragment != null && !isSearching)
            projectFragment.setItems(treeLevelItems);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_REPORT_RESULT) {
            if (resultCode == RESULT_OK) {
                reports = itemsTreeManager.getReports();
                reportFragment.setReports(reports);
            }
        }

        if (requestCode == CREATE_ITEM_RESULT) {
            if (resultCode == RESULT_OK) {
                items = itemsTreeManager.getItems();
                projectFragment.setItems(items);
            }
        }

        if (requestCode == EDIT_ITEM_RESULT) {
            if (resultCode == RESULT_OK) {
                //we set the new data of the item
                String name = data.getStringExtra("name");
                String description = data.getStringExtra("description");
                int color = data.getIntExtra("color", R.drawable.red);
                int position = data.getIntExtra("position", -1);
                boolean delete = data.getBooleanExtra("delete", false);

                //if we know the position of the item in the list
                if (position != -1) {
                    treeLevelItems.get(position).setName(name);
                    treeLevelItems.get(position).setDescription(description);
                    treeLevelItems.get(position).setColor(color);
                    itemsTreeManager.saveItems(items);
                }

                //in case we want to delete the item
                if (delete) {
                    treeLevelItems.remove(position);
                    itemsTreeManager.saveItems(items);
                }

                projectFragment.setItems(treeLevelItems);
            }
        }
    }

    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
