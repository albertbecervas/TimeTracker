package com.ds.timetracker.ui.timer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.ui.timer.adapter.viewholder.IntervalViewHolder;

import java.util.ArrayList;

public class IntervalsAdapter extends RecyclerView.Adapter<IntervalViewHolder> {

    private Context mContext;
    private ArrayList<Interval> intervals;

    public IntervalsAdapter(Context context, ArrayList<Interval> intervals) {
        this.mContext = context;
        this.intervals = intervals;
    }

    @Override
    public IntervalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntervalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_interval, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(IntervalViewHolder holder, int position) {
        holder.setInterval(intervals.get(position));
    }

    @Override
    public int getItemCount() {
        return intervals.size();
    }

    public void setIntervalsList(ArrayList<Interval> intervalsList) {
        intervals = intervalsList;
    }
}
