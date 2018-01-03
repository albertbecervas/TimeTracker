package com.ds.timetracker.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.model.observable.Clock;
import com.ds.timetracker.ui.edit.EditTaskActivity;
import com.ds.timetracker.ui.timer.adapter.IntervalsAdapter;
import com.ds.timetracker.ui.timer.callback.IntervalCallback;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TaskDetailActivity extends AppCompatActivity implements Observer, IntervalCallback {

    public static final int EDIT_TASK = 0;

    private RecyclerView recyclerView;
    private TextView description;
    private TextView duration;

    private IntervalsAdapter mAdapter;

    private Task mTask;
    private int position;

    boolean delete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        ItemsTreeManager itemsTreeManager = new ItemsTreeManager(this);


        ArrayList<Item> treeLevelItems = itemsTreeManager.getItems();

        position = getIntent().getIntExtra("position", -1);

        ArrayList<Integer> nodesReference;
        if (getIntent().hasExtra("nodesReference")) {
            nodesReference = getIntent().getIntegerArrayListExtra("nodesReference");
        } else {
            nodesReference = new ArrayList<>();
        }

        for (Integer i : nodesReference) {
            treeLevelItems = ((Project) treeLevelItems.get(i)).getItems();
        }

        mTask = (Task) treeLevelItems.get(position);

        setViews();
        setAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, EditTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", mTask);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_TASK);
                break;
            case R.id.delete:
                delete = true;
                onBackPressed();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void setAdapter() {
        mAdapter = new IntervalsAdapter(this, mTask.getIntervals());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        Clock.getInstance().addObserver(this);
    }

    private void setViews() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mTask.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        description = findViewById(R.id.description);
        TextView started = findViewById(R.id.started);
        TextView ended = findViewById(R.id.ended);
        duration = findViewById(R.id.duration);

        String startWd = mTask.getPeriod().getInitialFormattedDate() + getString(R.string.at) + mTask.getPeriod().getInitialFormattedHour();
        String endWd = "-";
        if (mTask.getPeriod().getFinalWorkingDate() != null) {
            endWd = mTask.getPeriod().getFinalFormattedDate() + getString(R.string.at) + mTask.getPeriod().getFinalFormattedHour();
        }

        description.setText(mTask.getDescription());
        started.setText(startWd);
        ended.setText(endWd);
        duration.setText(mTask.getFormattedDuration());

    }

    @Override
    public void onBackPressed() {
        Intent intent = this.getIntent();
        intent.putExtra("name", mTask.getName());
        intent.putExtra("description", mTask.getDescription());
        intent.putExtra("color", mTask.getColor());
        intent.putExtra("position", position);
        intent.putExtra("delete", delete);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TASK) {
            if (resultCode == RESULT_OK) {
                String nameStr = data.getStringExtra("name");
                String descriptionStr = data.getStringExtra("description");
                int color = data.getIntExtra("color", R.drawable.red);

                mTask.setName(nameStr);
                mTask.setDescription(descriptionStr);
                mTask.setColor(color);

                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(nameStr);
                description.setText(descriptionStr);
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (mTask != null && mAdapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    duration.setText(mTask.getFormattedDuration());
                    mAdapter.setIntervalsList(mTask.getIntervals());
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onIntervalDeleted(int position) {
        mTask.getIntervals().remove(position);
    }
}
