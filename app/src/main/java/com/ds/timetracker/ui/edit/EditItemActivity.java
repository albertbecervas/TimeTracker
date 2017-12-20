package com.ds.timetracker.ui.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Project;

public class EditItemActivity extends AppCompatActivity {

    private Project project;

    private EditText name;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project = (Project) bundle.getSerializable("project");
        }

        setToolbar();

        setViews();
    }

    private void setViews() {
        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);

        if (project != null) {
            name.setHint(project.getName());
            description.setHint(project.getDescription());
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_navigation_close);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
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
                editProject();
                break;
        }
        return true;
    }

    private void editProject() {
        String nameText = name.getText().toString();
        String descriptionText = description.getText().toString();
        if (isEmpty(nameText, descriptionText)) return;

        Intent intent = this.getIntent();
        intent.putExtra("name", nameText);
        intent.putExtra("description", descriptionText);
        setResult(RESULT_OK, intent);
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
