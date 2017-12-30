package com.ds.timetracker.ui.reports;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.MainActivity;
import com.ds.timetracker.ui.reports.adapter.ReportsAdapter;
import com.ds.timetracker.ui.reports.builders.Report;
import com.ds.timetracker.utils.ItemsTreeManager;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    private MainActivity activity;

    private ArrayList<Report> reports;

    private RecyclerView mRecyclerView; //RecyclerView used to display tasks and projects
    private ReportsAdapter mAdapter; //adapter that handles the items

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        reports = new ItemsTreeManager(activity).getReports();

        mRecyclerView = view.findViewById(R.id.reportsRV);

        setAdapter();

        // Inflate the layout for this fragment
        return view;
    }

    private void setAdapter() {
        mAdapter = new ReportsAdapter(reports, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setReports(ArrayList<Report> reports) {
        mAdapter.clearAdapter();
        mAdapter.setReportsList(reports);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
