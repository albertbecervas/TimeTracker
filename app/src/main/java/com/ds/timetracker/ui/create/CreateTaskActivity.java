package com.ds.timetracker.ui.create;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.utils.ItemsTreeManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private Spinner colorPicker;
    private CheckBox programmed;
    private CheckBox limited;
    private ConstraintLayout dateLayout;
    private ConstraintLayout hourLayout;

    private SpinnerAdapter adapter;

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
        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        colorPicker = findViewById(R.id.spinner);
        programmed = findViewById(R.id.programmed_switch);
        limited = findViewById(R.id.limited_switch);
        dateLayout = findViewById(R.id.from_layout);
        hourLayout = findViewById(R.id.time_layout);

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
        createTask();
        return false;
    }


    private void createTask(){
        String nameStr = name.getText().toString();
        String descriptionStr = description.getText().toString();

        if (nameStr.isEmpty()){
            name.setError("Cannot be empty");
            return;
        }

        if (descriptionStr.isEmpty()){
            description.setError("Cannot be empty");
            return;
        }


        for (Integer i : nodesReference) {
            fatherProject = ((Project) treeLevelItems.get(i));
            treeLevelItems = ((Project) treeLevelItems.get(i)).getItems();
        }

//        for (Integer position : nodesReference) {
//            if (items.get(position) instanceof Project)
//                fatherProject = ((Project) items.get(position));
//        }

        if (fatherProject != null) {
            fatherProject.newTask(nameStr, descriptionStr, color);
        } else {
            items.add(new Task(nameStr,descriptionStr,color,null));
        }

        new ItemsTreeManager(this).saveItems(items);

        setResult(RESULT_OK);
        finish();
    }
}
