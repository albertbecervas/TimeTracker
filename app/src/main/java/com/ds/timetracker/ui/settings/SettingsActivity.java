package com.ds.timetracker.ui.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.MainActivity;
import com.ds.timetracker.ui.create.SpinnerAdapter;
import com.ds.timetracker.utils.AppSharedPreferences;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private AppSharedPreferences mPrefs;

    private TextView titleLanguage;
    private TextView titleClock;
    Spinner languagePicker;
    SpinnerAdapter languageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMethod();

    }

    private void onCreateMethod() {

        setContentView(R.layout.activity_settings);

        mPrefs = AppSharedPreferences.getInstance(this);

        setViews();
    }


    private void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void setViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        titleLanguage = findViewById(R.id.title);
        titleClock = findViewById(R.id.title_clock);

        //spinner that sets the Locale of the phone in order to change the language
        setSpinnerAdapter();
        languagePicker.setSelection(getPosition());
        languagePicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String locale;
                boolean is24h = true;
                switch (i) {
                    case 0:
                        locale = "en";
                        is24h = false;
                        break;
                    case 1:
                        locale = "ca";
                        break;
                    case 2:
                        locale = "es";
                        break;
                    default:
                        locale = "en";
                        is24h = true;
                        break;
                }

                mPrefs.setLocale(locale);
                mPrefs.set24HFormat(is24h);

                /*setLocale(new Locale(mPrefs.getLocale()));

                setSpinnerAdapter();

                titleLanguage.setText(getString(R.string.language));
                titleClock.setText(getString(R.string.minimum_clock_time));*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinnerAdapter() {

        final String[] languagesNames = {getString(R.string.english), getString(R.string.catalan), getString(R.string.spanish)};
        final int languages[] = {R.drawable.usa, R.drawable.catalunya, R.drawable.spain};
        languagePicker = findViewById(R.id.spinner);
        languageAdapter = new SpinnerAdapter(this, languages, languagesNames);
        languagePicker.setAdapter(languageAdapter);
    }

    private int getPosition() {
        switch (mPrefs.getLocale()) {
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
