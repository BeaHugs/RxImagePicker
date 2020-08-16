package com.ui.timeline.poweradapter;


import androidx.annotation.NonNull;

public abstract class AbsFallbackAdapterDelegate<T, VH extends PowerViewHolder> extends AdapterDelegate<T, VH> {

  @Override final protected boolean isForViewType(@NonNull Object item, int position) {
    return true;
  }
}
