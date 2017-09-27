package com.ds.timetracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProjectActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private Button createProject;
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
        createProject = findViewById(R.id.create_project);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                String descriptionText = description.getText().toString();

                if (nameText.equals("")) {
                    name.setError("empty field");
                    return;
                }

                if (descriptionText.equals("")) {
                    description.setError("empty field");
                    return;
                }

                String key = mDatabase.child("Projects").push().getKey();
                mDatabase.child("Projects").child(key).child("name").setValue(nameText);
                mDatabase.child("Projects").child(key).child("description").setValue(descriptionText);
            }
        });
    }
}
