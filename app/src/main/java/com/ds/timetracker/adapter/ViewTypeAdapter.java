package com.ds.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ds.timetracker.R;
import com.ds.timetracker.adapter.viewholder.ProjectViewHolder;
import com.ds.timetracker.adapter.viewholder.TaskViewHolder;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;

import java.util.ArrayList;

public class ViewTypeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Object> itemsList;

    public ViewTypeAdapter(Context context, ArrayList<Object> items) {
        this.mContext = context;
        this.itemsList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return new ProjectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_project, parent, false), mContext);
            case 1:
                return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_task, parent, false), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = itemsList.get(position);

        if (item instanceof Project) {
            ((ProjectViewHolder) holder).setItem(item);
            return;
        }

        if (item instanceof Task)
            ((TaskViewHolder) holder).setItem(item);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) return 0;
        return itemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = itemsList.get(position);
        if (item instanceof Project) return 0;
        if (item instanceof Task) return 1;
        return 0;
    }

    public void clearAdapter() {
        int size = this.itemsList.size();
        this.itemsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setItemsList(ArrayList<Object> items) {
        itemsList.clear();
        itemsList.addAll(items);
        notifyDataSetChanged();
    }

}
