package com.ds.timetracker.ui.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.reports.builders.BriefReport;
import com.ds.timetracker.ui.reports.builders.DetailedReport;
import com.ds.timetracker.ui.reports.builders.Report;
import com.ds.timetracker.utils.AppSharedPreferences;
import com.ds.timetracker.utils.ItemsTreeManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.ds.timetracker.utils.Constants.BRIEF_REPORT;
import static com.ds.timetracker.utils.Constants.HTML_FORMAT;
import static com.ds.timetracker.utils.Constants.TEXT_FORMAT;

public class CreateReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private ItemsTreeManager itemsTreeManager;
    private AppSharedPreferences mPrefs;

    private SimpleDateFormat dateFormat;

    private Date initialDate;
    private Date finalDate;

    private EditText nameEditText;
    private Spinner spinner;
    private TextView fromDateText;
    private TextView toDateText;
    private TextView fromHourText;
    private TextView toHourText;
    private TextView fromFormat;
    private TextView toFormat;
    private RadioButton textFormat;
    private RadioButton htmlFormat;

    private Boolean isInitialDateSelected = false;
    private Boolean isInitialHourSelected = false;

    private String initialHourStr;
    private String finalHourStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        itemsTreeManager = new ItemsTreeManager(this);

        mPrefs = AppSharedPreferences.getInstance(this);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale(mPrefs.getLocale()));

        setDefaultDates();

        setViews();
    }

    private void setDefaultDates() {
        //we set the actual date from the beginning
        initialDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.MONTH, +1);
        finalDate = calendar.getTime();
    }

    private void setViews() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nameEditText = findViewById(R.id.name_edit);
        spinner = findViewById(R.id.spinner);
        fromDateText = findViewById(R.id.from_date);
        toDateText = findViewById(R.id.to_date);
        fromHourText = findViewById(R.id.from_hour);
        toHourText = findViewById(R.id.to_hour);
        textFormat = findViewById(R.id.txt_rb);
        htmlFormat = findViewById(R.id.html_rb);
        fromFormat = findViewById(R.id.format_from);
        toFormat = findViewById(R.id.format_to);

        fromDateText.setOnClickListener(this);
        toDateText.setOnClickListener(this);
        fromHourText.setOnClickListener(this);
        toHourText.setOnClickListener(this);

        String initialDateStr = dateFormat.format(initialDate);
        String finalDateStr = dateFormat.format(finalDate);

        //these are the default hours in the format: hh:mm
        initialHourStr = "08:00";
        finalHourStr = "08:00";

        fromDateText.setText(initialDateStr);
        toDateText.setText(finalDateStr);

        fromHourText.setText(initialHourStr);
        toHourText.setText(finalHourStr);

        //if local chosen is 12H format
        if (!mPrefs.is24HFormat()) {
            fromFormat.setText(R.string.am);
            toFormat.setText(R.string.am);
        }
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
                createReport();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);

        if (isInitialDateSelected) {
            //we set the initial report date
            initialDate = cal.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(initialDate);
            calendar.add(Calendar.MONTH, +1);
            finalDate = calendar.getTime();
            fromDateText.setText(dateFormat.format(initialDate));
            toDateText.setText(dateFormat.format(finalDate));
        } else {
            //we set the final report date
            finalDate = cal.getTime();
            toDateText.setText(dateFormat.format(finalDate));
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        String hour = getStringDate(hourOfDay);
        String min = getStringDate(minute);

        if (isInitialHourSelected) {
            initialHourStr = hour + ":" + min;
            fromHourText.setText(initialHourStr);
            fromFormat.setText(getFormat(view));
        } else {
            finalHourStr = hour + ":" + min;
            toHourText.setText(finalHourStr);
            toFormat.setText(getFormat(view));
        }

    }

    /**
     * This function sets the correct format of hours and minutes
     * @param dateHour is an hour or a minute that we want to format as: hh or mm
     * @return String formatted
     */
    @NonNull
    private String getStringDate(int dateHour) {
        String h = "0";
        if (dateHour < 10) {
            h += dateHour;
        } else {
            h = String.valueOf(dateHour);
        }
        return h;
    }

    /**
     * This function returns the Locale format
     * @param view in order to get selected date
     * @return String with format of the date (am/pm or 24h)
     */
    public String getFormat(TimePickerDialog view) {
        if (!mPrefs.is24HFormat()) {
            if (view.getSelectedTime().isAM())
                return getString(R.string.am);
            else return getString(R.string.pm);
        } else return getString(R.string.h);
    }

    /**
     * Function linked to the checkbox views in activity layout
     * @param view view clicked
     */
    public void onRadioButtonClick(View view) {

        switch (view.getId()) {
            case R.id.txt_rb:
                if (htmlFormat.isChecked())
                    htmlFormat.setChecked(false);
                break;
            case R.id.html_rb:
                if (textFormat.isChecked())
                    textFormat.setChecked(false);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_date:
                isInitialDateSelected = true;
                showDatePickerDialog();
                break;
            case R.id.to_date:
                isInitialDateSelected = false;
                showDatePickerDialog();
                break;
            case R.id.from_hour:
                isInitialHourSelected = true;
                showTimePickerDialog();
                break;
            case R.id.to_hour:
                isInitialHourSelected = false;
                showTimePickerDialog();
                break;
        }
    }

    private void showTimePickerDialog() {
        Calendar from = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CreateReportActivity.this,
                from.get(Calendar.YEAR),
                from.get(Calendar.MONTH),
                mPrefs.is24HFormat()
        );
        tpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CreateReportActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void createReport() {
        Report report;

        String type = spinner.getSelectedItem().toString();

        String reportName = nameEditText.getText().toString();
        if (reportName.isEmpty()) {
            nameEditText.setError(getString(R.string.emptyError));
            return;
        }

        String format;
        if (htmlFormat.isChecked()) {
            format = HTML_FORMAT;
        } else {
            format = TEXT_FORMAT;
        }

        String initialDateStr = dateFormat.format(initialDate) + " " + initialHourStr + ":00";
        String finalDateStr = dateFormat.format(finalDate) + " " + finalHourStr + ":00";

        if (type.equals(BRIEF_REPORT)) {
            report = new BriefReport(reportName, itemsTreeManager.getItems(), format, initialDateStr, finalDateStr);
        } else {
            report = new DetailedReport(reportName, itemsTreeManager.getItems(), format, initialDateStr, finalDateStr);
        }

        itemsTreeManager.saveReport(report);

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

}
