package com.ds.timetracker.ui.create;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.utils.DatePickerFragment;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText name;
    private EditText description;
    private CheckBox programmed;
    private CheckBox limited;
    private ConstraintLayout dateLayout;
    private ConstraintLayout hourLayout;
    private TextView fromDate;

    private Project fatherProject;
    private ArrayList<Item> items;
    private ArrayList<Item> treeLevelItems;
    private ArrayList<Integer> nodesReference;

    private int color = R.drawable.red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        items = new ItemsTreeManager(this).getItems();
        treeLevelItems = items;

        if (getIntent().hasExtra("nodesReference")) {
            nodesReference = getIntent().getIntegerArrayListExtra("nodesReference");
        } else {
            nodesReference = new ArrayList<>();
        }

        setViews();
    }

    private void setViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        Spinner colorPicker = findViewById(R.id.spinner);
        programmed = findViewById(R.id.programmed_switch);
        limited = findViewById(R.id.limited_switch);
        dateLayout = findViewById(R.id.from_layout);
        hourLayout = findViewById(R.id.time_layout);
        fromDate = findViewById(R.id.from_date);

        String[] colorsNames = {getString(R.string.red), getString(R.string.blue), getString(R.string.green)};
        final int colors[] = {R.drawable.red, R.drawable.blue, R.drawable.green};
        SpinnerAdapter adapter = new SpinnerAdapter(this, colors, colorsNames);
        colorPicker.setAdapter(adapter);
        colorPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = colors[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragmentFrom = new DatePickerFragment();
                fragmentFrom.show(getSupportFragmentManager(), "initialDate");
            }
        });

        programmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (programmed.isChecked()) {
                    dateLayout.setVisibility(View.VISIBLE);
                } else {
                    dateLayout.setVisibility(View.GONE);
                }
            }
        });

        limited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (limited.isChecked()) {
                    hourLayout.setVisibility(View.VISIBLE);
                } else {
                    hourLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                createTask();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);

        //formatting the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String finalDate = dateFormat.format(cal.getTime());

        fromDate.setText(finalDate);
    }


    private void createTask() {
        String nameStr = name.getText().toString();
        String descriptionStr = description.getText().toString();

        if (nameStr.isEmpty()) {
            name.setError(getString(R.string.emptyError));
            return;
        }

        if (descriptionStr.isEmpty()) {
            description.setError(getString(R.string.emptyError));
            return;
        }


        for (Integer i : nodesReference) {
            fatherProject = ((Project) treeLevelItems.get(i));
            treeLevelItems = ((Project) treeLevelItems.get(i)).getItems();
        }

        if (fatherProject != null) {
            fatherProject.newTask(nameStr, descriptionStr, color);
        } else {
            items.add(new Task(nameStr, descriptionStr, color, null));
        }

        new ItemsTreeManager(this).saveItems(items);

        setResult(RESULT_OK);
        finish();
    }
}
