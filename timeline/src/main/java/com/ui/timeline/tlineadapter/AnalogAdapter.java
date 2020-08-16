package com.ui.timeline.tlineadapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ui.timeline.Analog;
import com.ui.timeline.BaseViewHolder;
import com.ui.timeline.R;
import com.ui.timeline.poweradapter.SingleAdapter;


/**
 * Created by lin18 on 2017/8/23.
 */

public class AnalogAdapter extends SingleAdapter<Analog, AnalogAdapter.ChildViewHolder> {

    public AnalogAdapter(@Nullable Object listener) {
        super(listener);
    }

    @Override
    public ChildViewHolder onCreateVHolder(ViewGroup parent, int viewType) {
        return new ChildViewHolder(parent, R.layout.analog_item);
    }

    @Override
    public void onBindVHolder(ChildViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final Analog analog = getItem(position);
        final int color = ContextCompat.getColor(context,
                analog.isHead ? android.R.color.black : android.R.color.darker_gray);
        holder.title.setTextColor(color);
        holder.title.setText(analog.text);
        holder.subtitle.setTextColor(color);
        holder.subtitle.setText(analog.time);
    }

    static class ChildViewHolder extends BaseViewHolder {

        //@BindView(R.id.title)
        TextView title;
        //@BindView(R.id.subtitle)
        TextView subtitle;

        ChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);

        }

    }

}
