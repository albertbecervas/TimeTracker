package com.ds.timetracker.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.model.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {

    private ListView listView;
    private TextView description;
    private TextView started;
    private TextView ended;
    private TextView duration;
    private EditText name;
    private EditText descriptionEdit;

    private Task mTask;
    private int position;

    private ArrayList<Interval> intervals;

    MenuItem edit;
    private boolean isInEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mTask = (Task) bundle.getSerializable("task");
            position = getIntent().getIntExtra("position", -1);
            intervals = mTask != null ? mTask.getIntervals() : new ArrayList<Interval>();

            setViews();
            setAdapter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_detail_menu, menu);
        edit = menu.findItem(R.id.edit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                if (isInEditMode){
                    edit.setIcon(R.drawable.ic_mode_edit);
                    name.setVisibility(View.GONE);
                    descriptionEdit.setVisibility(View.GONE);
                    description.setVisibility(View.VISIBLE);
                    mTask.setName(name.getText().toString());
                    mTask.setDescription(descriptionEdit.getText().toString());
                    description.setText(mTask.getDescription());
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(mTask.getName());
                    isInEditMode = false;
                } else {
                    edit.setIcon(R.drawable.ic_action_tick);
                    description.setVisibility(View.GONE);
                    name.setVisibility(View.VISIBLE);
                    descriptionEdit.setVisibility(View.VISIBLE);
                    isInEditMode = true;
                }
                break;
        }
        return true;
    }

    private void setAdapter() {
        ArrayList<String> labels = new ArrayList<>();

        for (Interval interval : intervals) {
            labels.add(interval.toString());
        }

        ArrayAdapter itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(itemsAdapter);
    }

    private void setViews() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mTask.getName());

        listView = findViewById(R.id.listView);
        description = findViewById(R.id.description);
        started = findViewById(R.id.started);
        ended = findViewById(R.id.ended);
        duration = findViewById(R.id.duration);
        name = findViewById(R.id.name);
        descriptionEdit = findViewById(R.id.description_edit);

        String startWd = mTask.getPeriod().getStartWorkingDate().toString();
        String endWd = "-";
        if (mTask.getPeriod().getFinalWorkingDate() != null){
            endWd = mTask.getPeriod().getFinalWorkingDate().toString();
        }

        description.setText(mTask.getDescription());
        started.setText(startWd);
        ended.setText(endWd);
        duration.setText(mTask.getFormattedDuration());

    }

    @Override
    public void onBackPressed() {
        if (isInEditMode){
            edit.setIcon(R.drawable.ic_mode_edit);
            name.setVisibility(View.GONE);
            descriptionEdit.setVisibility(View.GONE);
            description.setVisibility(View.VISIBLE);
            isInEditMode = false;
        } else {
            Intent intent = this.getIntent();
            intent.putExtra("name", mTask.getName());
            intent.putExtra("description", mTask.getDescription());
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            super.onBackPressed();
        }
    }
}
