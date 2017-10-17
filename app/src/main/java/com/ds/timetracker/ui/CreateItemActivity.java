package com.ds.timetracker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.ds.timetracker.R;
import com.ds.timetracker.helpers.FirebaseHelper;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateItemActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText name;
    private EditText description;

    private Switch limited;
    private Switch programmed;

    private String reference = "";

    private String itemType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        if (getIntent().hasExtra("reference"))
            reference = getIntent().getStringExtra("reference");

        itemType = getIntent().getStringExtra("itemType");

        mDatabase = FirebaseDatabase.getInstance().getReference(reference);

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

        if (itemType.equals("1")){
            limited = findViewById(R.id.limited_switch);
            programmed = findViewById(R.id.programmed_switch);
        }
    }

    private void setItem(String nameText, String descriptionText) {
        if (itemType.equals("0")) {
            new FirebaseHelper(mDatabase).setItem(new Project(nameText, descriptionText));
        } else {
            new FirebaseHelper(mDatabase).setItem(new Task(nameText, descriptionText, limited.isChecked(),programmed.isChecked()));
        }
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
