package org.beahugs.imagepicker.ui;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.R;

import org.beahugs.imagepicker.utils.UriUtils;

import java.io.File;
import java.util.List;


public final class ImageSelectAdapter extends MyAdapter<String> {

    private final List<String> mSelectImages;

    public ImageSelectAdapter(Context context, List<String> images) {
        super(context);
        mSelectImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ImageView mImageView;
        private CheckBox mCheckBox;

        private ViewHolder() {
            super(R.layout.image_select_item);
            mImageView = (ImageView) findViewById(R.id.iv_image_select_image);
            mCheckBox = (CheckBox) findViewById(R.id.iv_image_select_check);
        }

        @Override
        public void onBindView(int position) {
            String imagePath = getItem(position);
            Log.i("onBindView",imagePath);
            Glide.with(getContext()).load(UriUtils.getImageContentUri(getContext(), imagePath)).into(mImageView);

            mCheckBox.setChecked(mSelectImages.contains(imagePath));
        }
    }

    @Override
    protected RecyclerView.LayoutManager generateDefaultLayoutManager(Context context) {
        return new GridLayoutManager(context, 3);
    }
}