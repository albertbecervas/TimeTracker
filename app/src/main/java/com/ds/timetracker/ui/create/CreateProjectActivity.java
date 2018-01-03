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
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;

public class CreateProjectActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;

    private Project fatherProject;
    private ArrayList<Item> items;//entire tree of items
    private ArrayList<Item> treeLevelItems;//lists of items that father project has
    private ArrayList<Integer> nodesReference;//lists of reference of searched nodes positions

    private int color = R.drawable.red;//default item color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        items = new ItemsTreeManager(this).getItems();

        treeLevelItems = items;

        if (getIntent().hasExtra("nodesReference")) {
            nodesReference = getIntent().getIntegerArrayListExtra("nodesReference");
        } else {
            //we are on the top level of the tree
            nodesReference = new ArrayList<>();
        }

        setViews();
    }

    private void setViews() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        Spinner colorPicker = findViewById(R.id.spinner);

        String[] colorsNames={getString(R.string.red),getString(R.string.blue),getString(R.string.green)};
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                createProject();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }


    private void createProject() {
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
            fatherProject.newProject(nameStr, descriptionStr, color);
        } else {
            items.add(new Project(nameStr, descriptionStr, color, null));
        }

        new ItemsTreeManager(this).saveItems(items);

        setResult(RESULT_OK);
        finish();
    }
}
