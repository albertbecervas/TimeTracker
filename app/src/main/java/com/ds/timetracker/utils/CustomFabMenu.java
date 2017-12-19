package com.ds.timetracker.utils;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ds.timetracker.R;
import com.ds.timetracker.ui.create.CreateItemActivity;

public class CustomFabMenu extends ConstraintLayout implements View.OnClickListener {

    public boolean isFABOpen = false;
    FloatingActionButton fab, fab1, fab2;
    LinearLayout fabLayout1, fabLayout2;
    View fabBGLayout;

    private CustomFabMenuCallback mCallback;

    public CustomFabMenu(Context context) {
        super(context);
        initViews(context);
    }

    public CustomFabMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public CustomFabMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }


    private void initViews(final Context context) {
        View rootView = inflate(context, R.layout.fab_menu_layout, this);

        mCallback = (CustomFabMenuCallback) context;

        fabLayout1 = rootView.findViewById(R.id.fabLayout1);
        fabLayout2 = rootView.findViewById(R.id.fabLayout2);
        fab = rootView.findViewById(R.id.fab);
        fab1 = rootView.findViewById(R.id.fab1);
        fab2 = rootView.findViewById(R.id.fab2);
        fabBGLayout = rootView.findViewById(R.id.fabBGLayout);

        fab.setOnClickListener(this);
        fabBGLayout.setOnClickListener(this);
        fabLayout1.setOnClickListener(this);
        fabLayout2.setOnClickListener(this);
    }

    public void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
    }

    public void closeFABMenu() {
        if (isFABOpen) {
            isFABOpen = false;
            fab.animate().rotationBy(-180);
            fabLayout1.animate().translationY(0);
            fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (!isFABOpen) {
                        fabLayout1.setVisibility(View.GONE);
                        fabLayout2.setVisibility(View.GONE);
                        fabBGLayout.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
                break;
            case R.id.fabBGLayout:
                closeFABMenu();
                break;
            case R.id.fabLayout1:
                mCallback.onCreateItemSelected(Constants.TASK);
                break;
            case R.id.fabLayout2:
                mCallback.onCreateItemSelected(Constants.PROJECT);
                break;
        }
    }
}
