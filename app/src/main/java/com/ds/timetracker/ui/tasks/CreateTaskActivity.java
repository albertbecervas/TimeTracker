package com.ds.timetracker.ui.tasks;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ds.timetracker.R;
import com.ds.timetracker.callback.TasksCallback;
import com.ds.timetracker.helpers.ProjectHelper;
import com.ds.timetracker.helpers.TasksHelper;
import com.ds.timetracker.model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateTaskActivity extends AppCompatActivity implements TasksCallback {

    private DatabaseReference mDatabase;

    private EditText name;
    private EditText description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

                setProject(nameText, descriptionText);
            }
        });
    }

    private void setProject(String nameText, String descriptionText) {
        new TasksHelper(mDatabase, getIntent().getStringExtra("projectID")).setTasks(nameText, descriptionText);
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

    @Override
    public void onTasksLoaded(ArrayList<Task> tasks) {

    }
}
