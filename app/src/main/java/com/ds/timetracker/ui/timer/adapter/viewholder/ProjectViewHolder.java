package com.ds.timetracker.ui.timer.adapter.viewholder;

import android.content.Context;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ds.timetracker.R;
import com.ds.timetracker.model.Item;
import com.ds.timetracker.model.Project;
import com.ds.timetracker.model.Task;
import com.ds.timetracker.ui.timer.callback.ItemCallback;

public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {

    private Project mProject;
    public TextView title;
    private TextView time;
    private LinearLayout color;
    private ConstraintLayout layout;
    final ConstraintLayout deleteLayout;
    final ConstraintLayout editLayout;

    private Context mContext;

    private ItemCallback mCallback;

    public ProjectViewHolder(View view, final Context mContext, final ItemCallback callback) {
        super(view);

        this.mCallback = callback;

        this.mContext = mContext;

        deleteLayout = view.findViewById(R.id.delete_container);
        editLayout = view.findViewById(R.id.edit_container);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onDeleteItem(getAdapterPosition());
            }
        });

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onEditProject(getAdapterPosition(), mProject);
            }
        });

        title = view.findViewById(R.id.project_title);
        time = view.findViewById(R.id.time);
        color = view.findViewById(R.id.colorLayout);
        layout = view.findViewById(R.id.constraintLayout);
    }

    public void setItem(Item project) {
        mProject = (Project) project;
        title.setText(project.getName());

        time.setText(mProject.getFormattedDuration());

        setColor(mProject);

    }

    private void setColor(Project project) {

        int colorId = project.getColor();

        switch (colorId){
            case R.drawable.red:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_800));
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_50));
                break;
            case R.drawable.blue:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_blue_800));
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.md_blue_50));
                break;
            case R.drawable.green:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_800));
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.md_green_50));
                break;
            default:
                color.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_800));
                layout.setBackgroundColor(mContext.getResources().getColor(R.color.md_red_50));
                break;
        }

    }

    @Override
    public void onClick(View view) {
        if (deleteLayout.getVisibility() == View.VISIBLE){
            deleteLayout.setVisibility(View.GONE);
            editLayout.setVisibility(View.GONE);
        } else {
            mCallback.onProjectItemSelected(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {

        Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE) ;
        assert vibe != null;
        vibe.vibrate(50);

//        if (deleteLayout.getVisibility() == View.VISIBLE){
//            deleteLayout.setVisibility(View.GONE);
//            editLayout.setVisibility(View.GONE);
//        } else {
            deleteLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.VISIBLE);
//        }
        return false;
    }
}
