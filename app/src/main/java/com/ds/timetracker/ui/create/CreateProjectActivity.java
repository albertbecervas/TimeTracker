package com.ds.timetracker.ui.create;

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
import com.ds.timetracker.utils.Constants;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;

public class CreateProjectActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private Spinner colorPicker;

    private SpinnerAdapter adapter;

    private Project fatherProject;
    private ArrayList<Item> items;
    private ArrayList<Item> treeLevelItems;
    private ArrayList<Integer> nodesReference;

    private int color = R.drawable.red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        items = new ItemsTreeManager(this).getItems();

        treeLevelItems = items;

        if (getIntent().hasExtra("nodesReference")) {
            nodesReference = getIntent().getIntegerArrayListExtra("nodesReference");
        } else {
            nodesReference = new ArrayList<>();
        }

        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        colorPicker = findViewById(R.id.spinner);

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

        for (Integer i : nodesReference) {
            fatherProject = ((Project) treeLevelItems.get(i));
            treeLevelItems = ((Project) treeLevelItems.get(i)).getItems();
        }

        if (fatherProject != null) {
            fatherProject.newProject(nameStr, descriptionStr, color);
        } else {
            items.add(new Project(nameStr, descriptionStr, color, null));
        }

        new ItemsTreeManager(this).saveItems(items);

        setResult(RESULT_OK);
        finish();
    }
}
