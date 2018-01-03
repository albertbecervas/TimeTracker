package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Interval;
import com.ds.timetracker.ui.timer.callback.IntervalCallback;
import com.ds.timetracker.utils.Constants;

public class IntervalViewHolder extends RecyclerView.ViewHolder {

    private TextView from;
    private TextView to;
    private TextView duration;
    private ConstraintLayout deleteContainer;

    private Context mContext;
    private IntervalCallback mCallback;

    public IntervalViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        this.mCallback = (IntervalCallback) context;

        from = itemView.findViewById(R.id.from_text);
        to = itemView.findViewById(R.id.to_text);
        duration = itemView.findViewById(R.id.duration);
        deleteContainer = itemView.findViewById(R.id.delete_container);
        deleteContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onIntervalDeleted(getAdapterPosition());
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteContainer.getVisibility() == View.VISIBLE){
                    deleteContainer.setVisibility(View.GONE);
                } else {
                    deleteContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setInterval(Interval interval) {

        String fromStr = mContext.getString(R.string.from)
                + interval.getInitialFormattedDate()
                + mContext.getString(R.string.at)
                + interval.getInitialFormattedHour();

        String toStr = mContext.getString(R.string.to)
                + interval.getFinalFormattedDate()
                + mContext.getString(R.string.at)
                + interval.getFinalFormattedHour();

        from.setText(fromStr);
        to.setText(toStr);
        duration.setText(interval.getFormattedDuration());
    }

}
