package com.ds.timetracker.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Settings;
import com.ds.timetracker.ui.MainActivity;
import com.ds.timetracker.ui.create.SpinnerAdapter;
import com.ds.timetracker.utils.AppSharedPreferences;

/**
 * This activity will display the settings of language and time for the user
 */
public class SettingsActivity extends AppCompatActivity {

    private AppSharedPreferences mPrefs;

    private Spinner languagePicker;

    private String[] secondsArray;
    private int clockSeconds = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        mPrefs = AppSharedPreferences.getInstance(this);

        setViews();

    }

    private void setViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        secondsArray = getResources().getStringArray(R.array.seconds_array);

        Spinner clockSecondsPicker = findViewById(R.id.spinner_clock);
        clockSecondsPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String seconds = secondsArray[i];

                if (seconds.equals(getString(R.string._1s))) {
                    clockSeconds = 1;
                }

                if (seconds.equals(getString(R.string._5s))) {
                    clockSeconds = 5;
                }

                if (seconds.equals(getString(R.string._1min))) {
                    clockSeconds = 60;
                }

                if (seconds.equals(getString(R.string._5min))) {
                    clockSeconds = 300;
                }

                if (seconds.equals(getString(R.string._1h))) {
                    clockSeconds = 3600;
                }

                mPrefs.setClockSeconds(clockSeconds);
                Settings.getInstance().setClockSeconds(mPrefs.getClockSeconds());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                        //selected english locale
                        locale = "en";
                        is24h = false;
                        break;
                    case 1:
                        locale = "ca";
                        //selected catalan locale
                        break;
                    case 2:
                        //selected spanish locale
                        locale = "es";
                        break;
                    default:
                        //selected english locale
                        locale = "en";
                        is24h = true;
                        break;
                }

                mPrefs.setLocale(locale);
                mPrefs.set24HFormat(is24h);

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
        SpinnerAdapter languageAdapter = new SpinnerAdapter(this, languages, languagesNames);
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
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
