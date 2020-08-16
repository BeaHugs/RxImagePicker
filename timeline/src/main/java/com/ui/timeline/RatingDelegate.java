package com.ui.timeline;

import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.ui.timeline.poweradapter.AdapterDelegate;
import com.ui.timeline.poweradapter.PowerViewHolder;

import java.util.List;

import static com.ui.timeline.Trace.RATING_TYPE;

/**
 *
 * Created by lin18 on 2017/8/23.
 */

public class RatingDelegate extends AdapterDelegate<Trace, PowerViewHolder> {


    public RatingDelegate() {
    }

    @Override
    protected boolean isForViewType(@NonNull Trace item, int position) {
        return RATING_TYPE.equals(item.type);
    }

    @NonNull
    @Override
    protected PowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ChildViewHolder(parent, R.layout.trace_rating);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Trace item, final int position, @NonNull PowerViewHolder holder, @NonNull List<Object> payloads) {
        final ChildViewHolder vh = (ChildViewHolder) holder;
        vh.rating.setRating((Float) item.value);
        vh.rating.setIsIndicator(true);
    }

    static class ChildViewHolder extends BaseViewHolder {

       // @BindView(R.id.rating)
        RatingBar rating;

        ChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);
            rating = itemView.findViewById(R.id.rating);
        }
    }

}
