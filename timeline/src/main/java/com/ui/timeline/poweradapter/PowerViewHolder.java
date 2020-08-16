package com.ui.timeline.poweradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder 基类
 * Created by lin18 on 2017/4/8.
 */
public class PowerViewHolder extends RecyclerView.ViewHolder {

    View contentView;
    public View itemView;

    public PowerViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        this(inflate(parent, layoutResId));
    }

    public PowerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public final View getContentView() {
        return contentView != null ? contentView : itemView;
    }

    public static View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }

}
