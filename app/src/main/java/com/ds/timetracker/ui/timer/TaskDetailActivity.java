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
import com.ds.timetracker.ui.edit.EditTaskActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {

    public static final int EDIT_TASK = 0;

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
                Intent intent = new Intent(this, EditTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", mTask);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_TASK);
                break;
            case android.R.id.home:
                onBackPressed();
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mTask.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listView = findViewById(R.id.listView);
        description = findViewById(R.id.description);
        started = findViewById(R.id.started);
        ended = findViewById(R.id.ended);
        duration = findViewById(R.id.duration);
        name = findViewById(R.id.name);
        descriptionEdit = findViewById(R.id.description_edit);

        String startWd = mTask.getPeriod().getStartWorkingDate().toString();
        String endWd = "-";
        if (mTask.getPeriod().getFinalWorkingDate() != null) {
            endWd = mTask.getPeriod().getFinalWorkingDate().toString();
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

                getSupportActionBar().setTitle(nameStr);
                description.setText(descriptionStr);
            }
        }
    }
}
