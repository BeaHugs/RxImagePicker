package com.ui.timeline;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.ui.timeline.poweradapter.AdapterDelegate;
import com.ui.timeline.poweradapter.PowerViewHolder;
import com.ui.timeline.tlineadapter.TraceAdapter;

import java.util.List;

import static com.ui.timeline.Trace.BUTTON_TYPE;


/**
 *
 * Created by lin18 on 2017/8/23.
 */

public class ButtonDelegate extends AdapterDelegate<Trace, PowerViewHolder> {

    private TraceAdapter.OnTraceClickListener clickListener;

    public ButtonDelegate(TraceAdapter.OnTraceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull Trace item, int position) {
        return BUTTON_TYPE.equals(item.type);
    }

    @NonNull
    @Override
    protected PowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ChildViewHolder(parent, R.layout.trace_button);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Trace item, final int position, @NonNull PowerViewHolder holder, @NonNull List<Object> payloads) {
        final Context context = holder.itemView.getContext();
        final ChildViewHolder vh = (ChildViewHolder) holder;

        vh.button.setText(item.name);
        vh.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onButtonClick(position);
            }
        });
    }

    static class ChildViewHolder extends BaseViewHolder {

        //@BindView(R.id.button)
        Button button;

        ChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);
            button = itemView.findViewById(R.id.button);
        }
    }

}
