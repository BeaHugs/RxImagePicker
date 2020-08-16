package com.ui.timeline;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;


import com.ui.timeline.poweradapter.PowerViewHolder;
import com.ui.timeline.tlineadapter.OrderAdapter;

import java.util.List;



/**
 *
 * Created by lin18 on 2017/8/23.
 */

public class InfoOrderDelegate extends AbsOrderDelegate {

    private OrderAdapter.OnOrderClickListener clickListener;

    public InfoOrderDelegate(OrderAdapter.OnOrderClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull Order item, int position) {
        return item instanceof InfoOrder;
    }

    @NonNull
    @Override
    protected PowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ChildViewHolder(parent, R.layout.order_info);
    }

    @Override
    protected void onBindViewHolder(@NonNull Order item, final int position, @NonNull PowerViewHolder holder, @NonNull List<Object> payloads) {
        super.onBindViewHolder(item, position, holder, payloads);
        final ChildViewHolder vh = (ChildViewHolder) holder;
        vh.look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onLookClick(position);
            }
        });
        vh.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCallClick(position);
            }
        });
    }

    static class ChildViewHolder extends AbsChildViewHolder {

        //@BindView(R.id.look)
        Button look;
        //@BindView(R.id.call)
        Button call;

        ChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);
            look = itemView.findViewById(R.id.look);
            call = itemView.findViewById(R.id.call);
//            look = parent.findViewById(R.id.look);
//            call = parent.findViewById(R.id.call);


        }
    }

}
