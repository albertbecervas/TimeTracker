package com.ds.timetracker.ui.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.MainActivity;
import com.ds.timetracker.ui.create.SpinnerAdapter;
import com.ds.timetracker.utils.AppSharedPreferences;

public class SettingsActivity extends AppCompatActivity {

    private Spinner languagePicker;
    private Spinner clockPicker;

    private SpinnerAdapter languageAdapter;
    private SpinnerAdapter clockAdapter;

    private AppSharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPrefs = AppSharedPreferences.getInstance(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        languagePicker = findViewById(R.id.spinner);
        clockPicker = findViewById(R.id.spinner_clock);

        final String[] languagesNames={"USA","CAT","ESP"};
        final int languages[] = {R.drawable.usa, R.drawable.spain, R.drawable.spain};
        languageAdapter = new SpinnerAdapter(this, languages, languagesNames);
        languagePicker.setAdapter(languageAdapter);
        languagePicker.setSelection(getPosition());
        languagePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String language = languagesNames[i];
                String locale = "en";
                switch (language){
                    case "USA":
                        locale = "en";
                        break;
                    case "CAT":
                        locale = "ca";
                        break;
                    case "ESP":
                        locale = "es";
                        break;
                }

                mPrefs.setLocale(locale);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private int getPosition() {
        switch (mPrefs.getLocale()){
            case "en":
                return 0;
            case "ca":
                return 1;
            case "es":
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
