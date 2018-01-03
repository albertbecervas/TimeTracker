package com.ds.timetracker.ui.reports.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.reports.adapter.viewholder.BriefReportViewHolder;
import com.ds.timetracker.ui.reports.adapter.viewholder.DetailedReportViewHolder;
import com.ds.timetracker.ui.reports.builders.BriefReport;
import com.ds.timetracker.ui.reports.builders.DetailedReport;
import com.ds.timetracker.ui.reports.builders.Report;

import java.util.ArrayList;

/**
 * This is the adapter in order to display in the correct way all reports that we have
 */
public class ReportsAdapter extends RecyclerView.Adapter {

    private ArrayList<Report> reports;
    private Context mContext;

    public ReportsAdapter(ArrayList<Report> reports, Context context) {
        this.reports = reports;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new BriefReportViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_report, parent, false), mContext);
            case 1:
                return new DetailedReportViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_report, parent, false), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Report report = reports.get(position);

        if (report instanceof BriefReport)
            ((BriefReportViewHolder) holder).setReport(report);
        else
            ((DetailedReportViewHolder) holder).setReport(report);
    }

    @Override
    public int getItemCount() {
        if (reports == null) return 0;
        return reports.size();
    }

    @Override
    public int getItemViewType(int position) {
        Report report = reports.get(position);
        if (report instanceof DetailedReport) return 1;
        else return 0;
    }

    public void clearAdapter() {
        int size = this.reports.size();
        this.reports.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setReportsList(ArrayList<Report> reportsList) {
        reports.clear();
        reports.addAll(reportsList);
    }

}
