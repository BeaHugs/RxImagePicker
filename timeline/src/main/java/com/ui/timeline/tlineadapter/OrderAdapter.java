package com.ui.timeline.tlineadapter;


import androidx.annotation.Nullable;


import com.ui.timeline.InfoOrderDelegate;
import com.ui.timeline.LocationOrderDelegate;
import com.ui.timeline.Order;
import com.ui.timeline.TextOrderDelegate;
import com.ui.timeline.poweradapter.DefaultAdapterDelegate;
import com.ui.timeline.poweradapter.MultiAdapter;
import com.ui.timeline.poweradapter.PowerViewHolder;

/**
 *
 * Created by lin18 on 2017/8/23.
 */

public class OrderAdapter extends MultiAdapter<Order, PowerViewHolder> {

    public OrderAdapter(@Nullable Object listener, @Nullable OnOrderClickListener clickListener) {
        super(listener);
        delegatesManager.addDelegate(new TextOrderDelegate());
        delegatesManager.addDelegate(new InfoOrderDelegate(clickListener));
        delegatesManager.addDelegate(new LocationOrderDelegate(clickListener));
        delegatesManager.setFallbackDelegate(new DefaultAdapterDelegate<Order>());
    }

    public interface OnOrderClickListener {
        void onLookClick(int position);

        void onCallClick(int position);

        void onLocationClick(int position);
    }

}