package com.ds.timetracker.ui.timer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.ui.timer.adapter.viewholder.ProjectViewHolder;
import com.ds.timetracker.ui.timer.adapter.viewholder.TaskViewHolder;
import com.ds.timetracker.ui.timer.callback.ItemCallback;
import com.ds.timetracker.utils.AppSharedPreferences;
import com.ds.timetracker.utils.Constants;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Item> itemsList;
    private ItemCallback mCallback;

    public ItemsAdapter(Context context, ItemCallback callback, ArrayList<Item> items) {
        this.mContext = context;
        this.mCallback = callback;
        this.itemsList = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                return new ProjectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_project, parent, false), mContext, mCallback);
            case 1:
                return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_task, parent, false), mContext, mCallback);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = itemsList.get(position);

        if (item.getType().equals(Constants.TASK))
            ((TaskViewHolder) holder).setItem(item);
        else
            ((ProjectViewHolder) holder).setItem(item);

    }

    @Override
    public int getItemCount() {
        if (itemsList == null) return 0;
        return itemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Item item = itemsList.get(position);
        if (item.getType().equals(Constants.TASK)) return 1;
        else return 0;
    }

    public void clearAdapter() {
        int size = this.itemsList.size();
        this.itemsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setItemsList(ArrayList<Item> items) {
        itemsList.clear();
        itemsList.addAll(items);
    }
}
