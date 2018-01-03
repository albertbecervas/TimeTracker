package com.ds.timetracker.ui.reports;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

/**
 * Reports fragment is one of the two main fragment displayed on the viewPager
 * Here we can see all reports and download them on internal phone storage
 */
public class ReportFragment extends Fragment {

    private MainActivity activity;

    private ArrayList<Report> reports;

    private RecyclerView mRecyclerView; //RecyclerView used to display tasks and projects
    private ReportsAdapter mAdapter; //adapter that handles the items
    private ConstraintLayout emptyLayout;//layout shown when there are no reports

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

        emptyLayout = view.findViewById(R.id.empty_layout);

        setAdapter();

        // Inflate the layout for this fragment
        return view;
    }

    private void setAdapter() {
        if (!reports.isEmpty()){
            mAdapter = new ReportsAdapter(reports, getActivity());
            emptyLayout.setVisibility(View.GONE);
        } else {
            //setting the no items screen
            mAdapter = new ReportsAdapter(new ArrayList<Report>(), getActivity());
            emptyLayout.setVisibility(View.VISIBLE);
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setReports(ArrayList<Report> reports) {

        if (!reports.isEmpty()){
            emptyLayout.setVisibility(View.GONE);
            mAdapter.clearAdapter();
            mAdapter.setReportsList(reports);
            mAdapter.notifyDataSetChanged();
        } else {
            //setting the no items screen
            mAdapter.clearAdapter();
            emptyLayout.setVisibility(View.VISIBLE);
        }
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
