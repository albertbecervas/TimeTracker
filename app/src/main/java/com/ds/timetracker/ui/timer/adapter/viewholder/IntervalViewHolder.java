package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;

public class IntervalViewHolder extends RecyclerView.ViewHolder {

    private TextView from;
    private TextView to;
    private TextView duration;

    public IntervalViewHolder(View itemView) {
        super(itemView);

        from = itemView.findViewById(R.id.from_text);
        to = itemView.findViewById(R.id.to_text);
        duration = itemView.findViewById(R.id.duration);
    }

    public void setInterval(Interval interval){
        from.setText(interval.getInitialFormattedDate());
        to.setText(interval.getFinalFormattedDate());
        duration.setText(interval.getFormattedDuration());
    }

}
