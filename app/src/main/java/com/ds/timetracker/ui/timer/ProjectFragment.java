package com.ds.timetracker.ui.timer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.ui.MainActivity;
import com.ds.timetracker.ui.edit.EditProjectActivity;
import com.ds.timetracker.ui.timer.adapter.ItemsAdapter;
import com.ds.timetracker.ui.timer.callback.ItemCallback;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Projects fragment is one of the two main fragment displayed on the viewPager
 * Here we can see all items, start and stop tasks and se the items details.
 * In addition, we have some options for the user in the menu
 */
public class ProjectFragment extends Fragment implements ItemCallback {

    private MainActivity activity;

    private RecyclerView mRecyclerView; //RecyclerView used to display tasks and projects
    private ItemsAdapter mAdapter; //adapter that handles the items

    private TextView level;
    private CircularProgressView progressView;
    private ConstraintLayout emptyLayout;
    private ImageButton backButton;

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

        emptyLayout = view.findViewById(R.id.empty_layout);

        backButton = view.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        setLevelTitle();

        setAdapter();

        return view;
    }

    private void setLevelTitle() {
        if (activity.father == null) {
            level.setText(R.string.main);
        } else {
            level.setText(activity.father.getName());
        }
    }

    private void setAdapter() {
        mAdapter = new ItemsAdapter(getActivity(), this, new ArrayList<Item>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setItems(final ArrayList<Item> items) {
        if (mAdapter != null && getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setLevelTitle();

                    if (!items.isEmpty()) {
                        emptyLayout.setVisibility(View.GONE);
                        progressView.setVisibility(View.VISIBLE);
                        mAdapter.clearAdapter();
                        Collections.sort(items);
                        mAdapter.setItemsList(items);
                        mAdapter.notifyDataSetChanged();
                        progressView.setVisibility(View.GONE);

                    } else {
                        mAdapter.clearAdapter();
                        emptyLayout.setVisibility(View.VISIBLE);
                        progressView.setVisibility(View.GONE);
                    }

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
        activity.itemsTreeManager.saveItems(activity.items);
    }

    @Override
    public void onProjectItemSelected(int position) {
        activity.nodesReference.add(position);

        //itearates over all projects in order to show the children.
        activity.treeLevelItems = activity.items;
        for (Integer i : activity.nodesReference) {
            activity.father = ((Project) activity.treeLevelItems.get(i));
            activity.treeLevelItems = ((Project) activity.treeLevelItems.get(i)).getItems();
        }

        if (!activity.treeLevelItems.isEmpty()) {
            emptyLayout.setVisibility(View.GONE);
            mAdapter.clearAdapter();
            mAdapter.setItemsList(activity.treeLevelItems);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.clearAdapter();
            emptyLayout.setVisibility(View.VISIBLE);
        }
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
        intent.putIntegerArrayListExtra("nodesReference", activity.nodesReference);
        activity.startActivityForResult(intent, MainActivity.EDIT_ITEM_RESULT);
    }
}
