package com.ui.timeline.poweradapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * Created by lin18 on 2017/4/26.
 */

public class ChildExpandViewHolder<C> extends PowerViewHolder {

    C mChild;
    ExpandableAdapter mExpandableAdapter;

    public ChildExpandViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        super(parent, layoutResId);
    }

    public ChildExpandViewHolder(View itemView) {
        super(itemView);
    }

    public C getChild() {
        return mChild;
    }

    public int getParentAdapterPosition() {
        int flatPosition = getAdapterPosition();
        if (mExpandableAdapter == null || flatPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        return mExpandableAdapter.getNearestParentPosition(flatPosition);
    }

    public int getChildAdapterPosition() {
        int flatPosition = getAdapterPosition();
        if (mExpandableAdapter == null || flatPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        return mExpandableAdapter.getChildPosition(flatPosition);
    }
}
