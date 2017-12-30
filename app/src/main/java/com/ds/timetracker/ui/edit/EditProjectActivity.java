package com.ds.timetracker.ui.edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.create.SpinnerAdapter;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;

public class EditProjectActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private Spinner colorPicker;

    private SpinnerAdapter adapter;

    private int color = R.drawable.red;

    private Project mProject;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mProject = (Project) bundle.getSerializable("project");
            position = getIntent().getIntExtra("position", -1);
        }

        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        colorPicker = findViewById(R.id.spinner);

        name.setText(mProject.getName());
        description.setText(mProject.getDescription());

        String[] colorsNames={"Red","Blue","Green"};
        final int colors[] = {R.drawable.red, R.drawable.blue, R.drawable.green};
        adapter = new SpinnerAdapter(this, colors, colorsNames);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        createProject();
        return false;
    }


    private void createProject() {
        String nameStr = name.getText().toString();
        String descriptionStr = description.getText().toString();

        if (nameStr.isEmpty()) {
            name.setError("Cannot be empty");
            return;
        }

        if (descriptionStr.isEmpty()) {
            description.setError("Cannot be empty");
            return;
        }

        Intent intent = this.getIntent();
        intent.putExtra("name", nameStr);
        intent.putExtra("description", descriptionStr);
        intent.putExtra("position", position);
        intent.putExtra("color", color);
        setResult(RESULT_OK, intent);
        finish();
    }
}
