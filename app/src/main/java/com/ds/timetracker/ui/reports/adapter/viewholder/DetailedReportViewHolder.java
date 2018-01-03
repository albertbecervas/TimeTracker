package com.ds.timetracker.ui.reports.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.reports.ReportDetailActivity;
import com.ds.timetracker.ui.reports.builders.DetailedReport;
import com.ds.timetracker.ui.reports.builders.Report;

public class DetailedReportViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView generationDate;

    private DetailedReport mReport;

    private Context mContext;

    public DetailedReportViewHolder(View itemView, final Context context) {
        super(itemView);
        this.mContext = context;

        title = itemView.findViewById(R.id.title);
        generationDate = itemView.findViewById(R.id.date);
        ImageView download = itemView.findViewById(R.id.download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mReport != null) {
                    mReport.generateDetailedReport(mContext);
                    Toast.makeText(mContext, "downloading", Toast.LENGTH_LONG).show();
                }
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,ReportDetailActivity.class);
                i.putExtra("report", mReport.getFormattedElementsToDisplay());
                i.putExtra("title", mReport.getName());
                mContext.startActivity(i);
            }
        });
    }

    public void setReport(Report report) {
        this.mReport = (DetailedReport) report;

        String titleStr = report.getName() + "." + report.getFormatStr();

        title.setText(titleStr);
        generationDate.setText(mReport.getGenerationDate());
    }
}