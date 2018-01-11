package com.ds.timetracker.ui.create;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds.timetracker.R;

/**
 * Adapter used to show a colors list
 */
public class SpinnerAdapter extends BaseAdapter {
    private int colors[];
    private String[] colorNames;
    private LayoutInflater inflater;

    public SpinnerAdapter(Context applicationContext, int[] flags, String[] countryNames) {
        this.colors = flags;
        this.colorNames = countryNames;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_list_color, null);
        ImageView icon = view.findViewById(R.id.imageView);
        TextView names = view.findViewById(R.id.textView);
        icon.setImageResource(colors[i]);
        names.setText(colorNames[i]);
        return view;
    }
}
