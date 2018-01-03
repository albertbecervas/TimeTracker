package com.ds.timetracker.ui.edit;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.create.SpinnerAdapter;

public class EditTaskActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private CheckBox programmed;
    private CheckBox limited;
    private ConstraintLayout dateLayout;
    private ConstraintLayout hourLayout;

    private Task mTask;

    private int color = R.drawable.red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mTask = (Task) bundle.getSerializable("task");
        }

        setViews();
    }

    private void setViews() {
        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        Spinner colorPicker = findViewById(R.id.spinner);
        programmed = findViewById(R.id.programmed_switch);
        limited = findViewById(R.id.limited_switch);
        dateLayout = findViewById(R.id.from_layout);
        hourLayout = findViewById(R.id.time_layout);

        name.setText(mTask.getName());
        description.setText(mTask.getDescription());

        String[] colorsNames={getString(R.string.red), getString(R.string.blue), getString(R.string.green)};
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

        programmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (programmed.isChecked()){
                    dateLayout.setVisibility(View.VISIBLE);
                } else {
                    dateLayout.setVisibility(View.GONE);
                }
            }
        });

        limited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(limited.isChecked()){
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
        editTask();
        return false;
    }


    private void editTask(){
        String nameStr = name.getText().toString();
        String descriptionStr = description.getText().toString();

        if (nameStr.isEmpty()){
            name.setError(getString(R.string.emptyError));
            return;
        }

        if (descriptionStr.isEmpty()){
            description.setError(getString(R.string.emptyError));
            return;
        }

        Intent intent = this.getIntent();
        intent.putExtra("name", nameStr);
        intent.putExtra("description", descriptionStr);
        intent.putExtra("color", color);
        setResult(RESULT_OK, intent);
        finish();
    }
}

