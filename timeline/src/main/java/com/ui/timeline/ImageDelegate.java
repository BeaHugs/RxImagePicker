package com.ui.timeline;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.ui.timeline.poweradapter.AdapterDelegate;
import com.ui.timeline.poweradapter.PowerViewHolder;
import com.ui.timeline.tlineadapter.TraceAdapter;

import java.util.List;

import static com.ui.timeline.Trace.IMG_TYPE;

/**
 *
 * Created by lin18 on 2017/8/23.
 */

public class ImageDelegate extends AdapterDelegate<Trace, PowerViewHolder> {

    private int width;
    private int height;
    private TraceAdapter.OnTraceClickListener clickListener;

    public ImageDelegate(int width, int height, TraceAdapter.OnTraceClickListener clickListener) {
        this.width = width;
        this.height = height;
        this.clickListener = clickListener;
    }

    @Override
    protected boolean isForViewType(@NonNull Trace item, int position) {
        return IMG_TYPE.equals(item.type);
    }

    @NonNull
    @Override
    protected PowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ChildViewHolder(parent, R.layout.trace_image);
    }

    @Override
    protected void onBindViewHolder(@NonNull Trace item, final int position, @NonNull PowerViewHolder holder, @NonNull List<Object> payloads) {
        final Context context = holder.itemView.getContext();
        final ChildViewHolder vh = (ChildViewHolder) holder;

//        Glide.with(context).load(((Picture)item.value).thumb)
//                .apply(new RequestOptions()
//                        .override(width, height)
//                        .placeholder(AppCompatResources.getDrawable(context, R.drawable.vector_default_image))
//                        .error(AppCompatResources.getDrawable(context, R.drawable.vector_default_image))
//                        .centerCrop()
//                        .priority(Priority.HIGH)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE))
//                .into(vh.imageView);

        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.onImageClick(position);
            }
        });
    }

    static class ChildViewHolder extends BaseViewHolder {

        //@BindView(R.id.imageView)
        ImageView imageView;

        ChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
