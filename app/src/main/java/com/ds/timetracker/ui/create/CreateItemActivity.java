package com.ds.timetracker.ui.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.utils.Constants;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;

public class CreateItemActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;

    private ArrayList<Item> items;
    private ArrayList<Integer> nodesReference;
    private ArrayList<Item> treeLevelItems;
    private Project project;

    private String itemType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        items = new ItemsTreeManager(this).getItems();
        treeLevelItems = items;

        if (getIntent().hasExtra("itemType")) {
            itemType = getIntent().getStringExtra("itemType");
            nodesReference = getIntent().getIntegerArrayListExtra("nodesReference");
        } else {
            itemType = Constants.TASK;
            nodesReference = new ArrayList<>();
        }

        setViews();
    }

    private void setViews() {
        Button createProject = findViewById(R.id.create_project);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);

        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                String descriptionText = description.getText().toString();
                if (isEmpty(nameText, descriptionText)) return;

                setItem(nameText, descriptionText);
            }
        });

        if (itemType.equals(Constants.TASK)) {
            Switch limited = findViewById(R.id.limited_switch);
            Switch programmed = findViewById(R.id.programmed_switch);
            limited.setVisibility(View.VISIBLE);
            programmed.setVisibility(View.VISIBLE);

        }
    }

    private void setItem(String nameText, String descriptionText) {

        for (Integer position : nodesReference) {
            project = ((Project) items.get(position));
        }

        if (itemType.equals(Constants.PROJECT)) {
            project.newProject(nameText,descriptionText);
        } else {
            project.newTask(nameText,descriptionText);
        }
        new ItemsTreeManager(this).saveItems(items);

        finish();
    }

    private boolean isEmpty(String nameText, String descriptionText) {
        if (nameText.equals("")) {
            name.setError("empty field");
            return true;
        }

        if (descriptionText.equals("")) {
            description.setError("empty field");
            return true;
        }
        return false;
    }
}
