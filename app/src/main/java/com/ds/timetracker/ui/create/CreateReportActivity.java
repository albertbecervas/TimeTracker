package com.ds.timetracker.ui.create;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.reports.builders.BriefReport;
import com.ds.timetracker.ui.reports.builders.DetailedReport;
import com.ds.timetracker.ui.reports.builders.Report;
import com.ds.timetracker.utils.DatePickerFragment;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.ds.timetracker.utils.Constants.BRIEF_REPORT;
import static com.ds.timetracker.utils.Constants.HTML_FORMAT;
import static com.ds.timetracker.utils.Constants.TEXT_FORMAT;

public class CreateReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private ItemsTreeManager itemsTreeManager;

    SimpleDateFormat dateFormat;

    private Date initialDate;
    private Date finalDate;

    private EditText nameEditText;
    private Spinner spinner;
    private TextView fromDateText;
    private TextView toDateText;
    private TextView fromHourText;
    private TextView toHourText;
    private RadioButton textFormat;
    private RadioButton htmlFormat;

    private Boolean isInitialDateSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        itemsTreeManager = new ItemsTreeManager(this);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        setDefaultDates();

        setViews();
    }

    private void setDefaultDates() {
        initialDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.MONTH, +1);
        finalDate = calendar.getTime();
    }

    private void setViews() {
        nameEditText = findViewById(R.id.name_edit);
        spinner = findViewById(R.id.spinner);
        fromDateText = findViewById(R.id.from_date);
        toDateText = findViewById(R.id.to_date);
        fromHourText = findViewById(R.id.from_hour);
        toHourText = findViewById(R.id.to_hour);
        textFormat = findViewById(R.id.txt_rb);
        htmlFormat = findViewById(R.id.html_rb);

        fromDateText.setOnClickListener(this);
        toDateText.setOnClickListener(this);
        fromHourText.setOnClickListener(this);
        toHourText.setOnClickListener(this);

        String initialDateStr = dateFormat.format(initialDate);
        String finalDateStr = dateFormat.format(finalDate);

        fromDateText.setText(initialDateStr);
        toDateText.setText(finalDateStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        createReport();
        return false;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);

        if (isInitialDateSelected) {
            initialDate = cal.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(initialDate);
            calendar.add(Calendar.MONTH, +1);
            finalDate = calendar.getTime();
            fromDateText.setText(dateFormat.format(initialDate));
            toDateText.setText(dateFormat.format(finalDate));
        } else {
            finalDate = cal.getTime();
            toDateText.setText(dateFormat.format(finalDate));
        }


    }


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
                DatePickerFragment fragmentFrom = new DatePickerFragment();
                fragmentFrom.show(getSupportFragmentManager(), "initialDate");
                break;
            case R.id.to_date:
                isInitialDateSelected = false;
                DatePickerFragment fragmentTo = new DatePickerFragment();
                fragmentTo.show(getSupportFragmentManager(), "finalDate");
                break;
            case R.id.from_hour:
                break;
            case R.id.to_hour:
                break;
        }
    }

    public void createReport() {
        Report report;

        String type = spinner.getSelectedItem().toString();

        String reportName = nameEditText.getText().toString();
        if (reportName.isEmpty()) {
            nameEditText.setError("Cannot be empty");
            return;
        }

        String format;
        if (htmlFormat.isChecked()) {
            format = HTML_FORMAT;
        } else {
            format = TEXT_FORMAT;
        }

        String initialDateStr = dateFormat.format(initialDate) + " " + fromHourText.getText() + ":00";
        String finalDateStr = dateFormat.format(finalDate) + " " + toHourText.getText() + ":00";

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