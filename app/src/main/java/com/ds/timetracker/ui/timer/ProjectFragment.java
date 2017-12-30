package com.ds.timetracker.ui.timer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.edit.EditProjectActivity;
import com.ds.timetracker.ui.main.MainActivity;
import com.ds.timetracker.ui.timer.adapter.ViewTypeAdapter;
import com.ds.timetracker.ui.timer.callback.ItemCallback;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;


public class ProjectFragment extends Fragment implements ItemCallback {

    private MainActivity activity;

    private RecyclerView mRecyclerView; //RecyclerView used to display tasks and projects
    private ViewTypeAdapter mAdapter; //adapter that handles the items

    private TextView level;
    private CircularProgressView progressView;

    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        mRecyclerView = view.findViewById(R.id.projectsRV);

        level = view.findViewById(R.id.level_text);

        progressView = view.findViewById(R.id.progress);

        setLevelTitle();

        setAdapter();

        return view;
    }

    private void setLevelTitle() {
        if (activity.father == null){
            level.setText("MAIN");
        } else {
            level.setText(activity.father.getName());
        }
    }

    private void setAdapter() {
        mAdapter = new ViewTypeAdapter(getActivity(), this, new ArrayList<Item>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void resetAdapterItems(ArrayList<Item> items){
        mAdapter = new ViewTypeAdapter(getActivity(), this, items);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setItems(final ArrayList<Item> items) {
        if (mAdapter != null) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressView.setVisibility(View.VISIBLE);
                    setLevelTitle();
                    mAdapter.clearAdapter();
                    mAdapter.setItemsList(items);
                    mAdapter.notifyDataSetChanged();
                    progressView.setVisibility(View.GONE);

                }
            });

        }
    }

    public void setSearchResults(ArrayList<Item> searchResults) {
        mAdapter.clearAdapter();
        if (!searchResults.isEmpty()) {
            mAdapter.setItemsList(searchResults);
        } else {
            mAdapter.setItemsList(activity.items);
        }
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
        activity = null;
    }

    @Override
    public void onItemStateChanged() {

    }

    @Override
    public void onProjectItemSelected(int position) {
        activity.nodesReference.add(position);


        activity.treeLevelItems = activity.items;
        for (Integer i : activity.nodesReference) {
            activity.father = ((Project) activity.treeLevelItems.get(i));
            activity.treeLevelItems = ((Project) activity.treeLevelItems.get(i)).getItems();
        }

        mAdapter.clearAdapter();
        mAdapter.setItemsList(activity.treeLevelItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteItem(int position) {
        activity.treeLevelItems.remove(position);
    }

    @Override
    public void onEditProject(int position, Project project) {
        Intent intent = new Intent(activity, EditProjectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        intent.putExtras(bundle);
        intent.putExtra("position", position);
        activity.startActivityForResult(intent, MainActivity.EDIT_ITEM_RESULT);
    }

    @Override
    public void onEditTask(int position, Intent intent) {
        //say the position we want to edit
        activity.startActivityForResult(intent, MainActivity.EDIT_ITEM_RESULT);
    }
}
