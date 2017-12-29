package com.ds.timetracker.ui.create;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ds.timetracker.R;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    int colors[];
    String[] colorNames;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, int[] flags, String[] countryNames) {
        this.context = applicationContext;
        this.colors = flags;
        this.colorNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_list_color, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(colors[i]);
        names.setText(colorNames[i]);
        return view;
    }
}
