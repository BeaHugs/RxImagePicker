package com.ui.timeline.tlineadapter;



import androidx.annotation.Nullable;

import com.ui.timeline.ButtonDelegate;
import com.ui.timeline.ImageDelegate;
import com.ui.timeline.MarkerDelegate;
import com.ui.timeline.RatingDelegate;
import com.ui.timeline.TextDelegate;
import com.ui.timeline.TimeDelegate;
import com.ui.timeline.Trace;
import com.ui.timeline.poweradapter.DefaultAdapterDelegate;
import com.ui.timeline.poweradapter.MultiAdapter;
import com.ui.timeline.poweradapter.PowerViewHolder;

/**
 *
 * Created by lin18 on 2017/8/23.
 */

public class TraceAdapter extends MultiAdapter<Trace, PowerViewHolder> {

    public TraceAdapter(int width, int height,
                        @Nullable Object listener, @Nullable OnTraceClickListener clickListener) {
        super(listener);
        delegatesManager.addDelegate(new MarkerDelegate());
        delegatesManager.addDelegate(new TextDelegate());
        delegatesManager.addDelegate(new TimeDelegate());
        delegatesManager.addDelegate(new ImageDelegate(width, height, clickListener));
        delegatesManager.addDelegate(new ButtonDelegate(clickListener));
        delegatesManager.addDelegate(new RatingDelegate());
        delegatesManager.setFallbackDelegate(new DefaultAdapterDelegate<Trace>());
    }

    public interface OnTraceClickListener {
        void onImageClick(int position);

        void onButtonClick(int position);
    }

}