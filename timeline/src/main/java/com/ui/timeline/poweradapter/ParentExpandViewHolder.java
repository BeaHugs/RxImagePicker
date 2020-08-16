package com.ui.timeline.poweradapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ui.timeline.poweradapter.model.Parent;


/**
 *
 * Created by lin18 on 2017/4/26.
 */

public class ParentExpandViewHolder<P extends Parent<C>, C> extends PowerViewHolder {

    P mParent;
    ExpandableAdapter mExpandableAdapter;

    public ParentExpandViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        super(parent, layoutResId);
    }

    public ParentExpandViewHolder(View itemView) {
        super(itemView);
    }

    public P getParent() {
        return mParent;
    }

    public int getParentAdapterPosition() {
        int flatPosition = getAdapterPosition();
        if (flatPosition == RecyclerView.NO_POSITION) {
            return flatPosition;
        }

        return mExpandableAdapter.getNearestParentPosition(flatPosition);
    }

    public void onExpansionToggled(boolean expanded) {

    }

    public boolean shouldItemViewClickToggleExpansion() {
        return true;
    }

    protected void expandView() {
        onExpansionToggled(false);
    }

    protected void collapseView() {
        onExpansionToggled(true);
    }

    interface ParentViewHolderExpandCollapseListener {

        void onParentExpanded(int flatParentPosition);

        void onParentCollapsed(int flatParentPosition);
    }

}
