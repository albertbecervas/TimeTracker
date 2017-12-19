package com.ds.timetracker.ui.reports;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.create.CreateReportFragment;
import com.ds.timetracker.ui.timer.ProjectDetailActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class ReportsActivity extends AppCompatActivity {

    private boolean isFragmentVisible = false;

    private CreateReportFragment mFragment;

    private FrameLayout frameLayout;
    private View bgLayout;
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        setViews();
    }

    protected void setViews() {
        mFragment = new CreateReportFragment();
        frameLayout = findViewById(R.id.create_report_fragment);
        frameLayout.bringToFront();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.create_report_fragment, mFragment, "create fragment");
        fragmentTransaction.commit();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView =  findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
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


        bgLayout = findViewById(R.id.BGLayout);

        bgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentVisible){
                    frameLayout.setVisibility(View.VISIBLE);
                    bgLayout.setVisibility(View.VISIBLE);
                    isFragmentVisible = true;
                } else {
                    frameLayout.setVisibility(View.GONE);
                    bgLayout.setVisibility(View.GONE);
                    isFragmentVisible = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reports_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timer:
                startActivity(new Intent(this, ProjectDetailActivity.class));
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isFragmentVisible){
            frameLayout.setVisibility(View.GONE);
            bgLayout.setVisibility(View.GONE);
            isFragmentVisible = false;
        } else super.onBackPressed();

    }
}
