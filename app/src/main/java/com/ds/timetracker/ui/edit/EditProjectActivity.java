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
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.create.SpinnerAdapter;

public class EditProjectActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;

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
            //position of the project on the list
            position = getIntent().getIntExtra("position", -1);
        }

        setViews();
    }

    private void setViews() {
        name = findViewById(R.id.name_edit);
        description = findViewById(R.id.description_edit);
        Spinner colorPicker = findViewById(R.id.spinner);

        name.setText(mProject.getName());
        description.setText(mProject.getDescription());

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
        editProject();
        return false;
    }


    private void editProject() {
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

        //Set the data that we want to give back to the activity in order to edit the project
        Intent intent = this.getIntent();
        intent.putExtra("name", nameStr);
        intent.putExtra("description", descriptionStr);
        intent.putExtra("position", position);
        intent.putExtra("color", color);
        setResult(RESULT_OK, intent);
        finish();
    }
}
