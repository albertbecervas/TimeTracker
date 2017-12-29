package com.ds.timetracker.ui.reports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ds.timetracker.R;

public class ReportDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        TextView report = findViewById(R.id.report);

        String reportText = getIntent().getStringExtra("report");

        String title = getIntent().getStringExtra("title");
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);

        report.setText(reportText);
    }
}
