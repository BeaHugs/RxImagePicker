package com.ui.timeline;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.ui.timeline.poweradapter.AdapterDelegate;
import com.ui.timeline.poweradapter.PowerViewHolder;

import java.util.List;


/**
 *
 * Created by lin18 on 2017/8/23.
 */

public abstract class AbsOrderDelegate extends AdapterDelegate<Order, PowerViewHolder> {

    @CallSuper
    @Override
    protected void onBindViewHolder(@NonNull Order item, int position, @NonNull PowerViewHolder holder, @NonNull List<Object> payloads) {
        final Context context = holder.itemView.getContext();
        final AbsChildViewHolder vh = (AbsChildViewHolder) holder;
        final int color = ContextCompat.getColor(context, item.isHead ? R.color.colorAccent : android.R.color.darker_gray);
        vh.time.setTextColor(color);
        vh.time.setText(item.time);
        vh.title.setTextColor(color);
        vh.title.setText(item.title);
        vh.subtitle.setTextColor(color);
        vh.subtitle.setText(item.subTitle);
        vh.subtitle.setVisibility(TextUtils.isEmpty(item.subTitle) ? View.GONE : View.VISIBLE);
    }

    static class AbsChildViewHolder extends BaseViewHolder {

        //@BindView(R.id.time)
        TextView time;
        //@BindView(R.id.title)
        TextView title;
        //@BindView(R.id.subtitle)
        TextView subtitle;

        AbsChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);


            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);

        }
    }

}
