package org.beahugs.imagepicker.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.imageselector.R;

import org.beahugs.imagepicker.VideoPlayActivity;
import org.beahugs.imagepicker.config.MimeType;
import org.beahugs.imagepicker.dialog.PictureCustomDialog;
import org.beahugs.imagepicker.entry.Image;
import org.beahugs.imagepicker.utils.DateUtils;
import org.beahugs.imagepicker.utils.MediaUtils;
import org.beahugs.imagepicker.utils.VersionUtils;

import java.util.ArrayList;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Image> mImages;
    private LayoutInflater mInflater;

    //保存选中的图片
    private ArrayList<Image> mSelectImages = new ArrayList<>();
    private OnImageSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;
    private int mMaxCount;
    private boolean isSingle;
    private boolean isViewImage;

    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_IMAGE = 2;

    //private boolean useCamera;

    private boolean isAndroidQ = VersionUtils.isAndroidQ();

    /**
     * @param maxCount    图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     * @param isSingle    是否单选
     * @param isViewImage 是否点击放大图片查看
     */
    public ImageAdapter(Context context, int maxCount, boolean isSingle, boolean isViewImage) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mMaxCount = maxCount;
        this.isSingle = isSingle;
        this.isViewImage = isViewImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            View view = mInflater.inflate(R.layout.adapter_images_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.adapter_camera, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_IMAGE) {
            final Image image = getImage(position);


            Glide.with(mContext).load(isAndroidQ ? image.getUri() : image.getPath())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(holder.ivImage);

            String mimeType = image.getMimeType();


            final boolean isImage = MimeType.eqImage(mimeType);

            Log.i("image.getPath", isImage + "");
            //2020年3月8日  视频格式
            if (!isImage) {
                holder.tv_duration.setVisibility(View.VISIBLE);
                //String path = image.getUri().getPath();
                Log.i("image.getPath", image.getPath());
                String video_time = DateUtils.formatDurationTime(MediaUtils.extractDuration(mContext, VersionUtils.isAndroidQ(), image.getPath()));
                holder.tv_duration.setText(video_time);
            }


            setItemSelect(holder, mSelectImages.contains(image));

            holder.ivGif.setVisibility(image.isGif() ? View.VISIBLE : View.GONE);


            //点击选中/取消选中图片
            holder.ivSelectIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkedImage(holder, image);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isImage) {
                        if (isViewImage) {
                            if (mItemClickListener != null) {
                                int p = holder.getAdapterPosition();
                                //mItemClickListener.OnItemClick(image, useCamera ? p - 1 : p);
                            }
                        } else {
                            //checkedImage(holder, image);
                        }
                    } else {
                        //Toast.makeText(mContext,"视频不支持选择___待开发",Toast.LENGTH_LONG).show();
                        //image.getPath();
                        VideoPlayActivity.start(mContext, image.getPath());

                    }
                }
            });

            maskProcessing(holder, image);
        } else if (getItemViewType(position) == TYPE_CAMERA) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnCameraClick();
                    }
                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
//        if (useCamera && position == 0) {
//            return TYPE_CAMERA;
//        } else {
        return TYPE_IMAGE;
        // }
    }

    private void checkedImage(ViewHolder holder, Image image) {
        if (mSelectImages.contains(image)) {
            //如果图片已经选中，就取消选中
            unSelectImage(image);
            setItemSelect(holder, false);
            notifyDataSetChanged();
        } else if (isSingle) {
            //如果是单选，就先清空已经选中的图片，再选中当前图片
            clearImageSelect();
            selectImage(image);
            setItemSelect(holder, true);
        } else if (mMaxCount <= 0 || mSelectImages.size() < mMaxCount) {

            //如果不限制图片的选中数量，或者图片的选中数量
            // 还没有达到最大限制，就直接选中当前图片。
            selectImage(image);
            setItemSelect(holder, true);

            if (mSelectImages.size() >= mMaxCount) {
                notifyDataSetChanged();
            }
        } else if (mSelectImages.size() >= mMaxCount) {
            showPromptDialog("您最多选择" + mMaxCount + "张图片");
            return;
        }


    }

    /**
     * 选中图片
     *
     * @param image
     */
    private void selectImage(Image image) {
        mSelectImages.add(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, true, mSelectImages.size());
        }
    }

    /**
     * 取消选中图片
     *
     * @param image
     */
    private void unSelectImage(Image image) {
        mSelectImages.remove(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, false, mSelectImages.size());
        }
    }


    @Override
    public int getItemCount() {
        return getImageCount();
    }

    private int getImageCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public ArrayList<Image> getData() {
        return mImages;
    }

    public void refresh(ArrayList<Image> data) {
        mImages = data;
        // this.useCamera = useCamera;
        notifyDataSetChanged();
    }

    private Image getImage(int position) {
        return mImages.get(position);
    }

    public Image getFirstVisibleImage(int firstVisibleItem) {
        if (mImages != null && !mImages.isEmpty()) {
            //if (useCamera) {
            //  return mImages.get(firstVisibleItem > 0 ? firstVisibleItem - 1 : 0);
            //} else {
            return mImages.get(firstVisibleItem < 0 ? 0 : firstVisibleItem);
            //}
        }
        return null;
    }

    /**
     * 设置图片选中和未选中的效果
     */
    private void setItemSelect(ViewHolder holder, boolean isSelect) {
        if (isSelect) {
            holder.ivSelectIcon.setImageResource(R.drawable.icon_image_select);
            holder.ivMasking.setAlpha(0.5f);
        } else {
            holder.ivSelectIcon.setImageResource(R.drawable.icon_image_un_select);
            holder.ivMasking.setAlpha(0.2f);
        }
    }

    private void clearImageSelect() {
        if (mImages != null && mSelectImages.size() == 1) {
            int index = mImages.indexOf(mSelectImages.get(0));
            mSelectImages.clear();
            if (index != -1) {
                notifyItemChanged(index);
            }
        }
    }

    public void setSelectedImages(ArrayList<String> selected) {
        if (mImages != null && selected != null) {
            for (String path : selected) {
                if (isFull()) {
                    return;
                }
                for (Image image : mImages) {
                    if (path.equals(image.getPath())) {
                        if (!mSelectImages.contains(image)) {
                            mSelectImages.add(image);
                        }
                        break;
                    }
                }
            }
            notifyDataSetChanged();
        }
    }


    private boolean isFull() {
        if (isSingle && mSelectImages.size() == 1) {
            return true;
        } else if (mMaxCount > 0 && mSelectImages.size() == mMaxCount) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Image> getSelectImages() {
        return mSelectImages;
    }

    public void setOnImageSelectListener(OnImageSelectListener listener) {
        this.mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        ImageView ivSelectIcon;
        ImageView ivMasking;
        ImageView ivGif;
        ImageView ivCamera;
        TextView tv_duration;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            ivSelectIcon = itemView.findViewById(R.id.iv_select);
            ivMasking = itemView.findViewById(R.id.iv_masking);
            ivGif = itemView.findViewById(R.id.iv_gif);

            ivCamera = itemView.findViewById(R.id.iv_camera);
            tv_duration = itemView.findViewById(R.id.tv_duration);
        }
    }

    public interface OnImageSelectListener {
        void OnImageSelect(Image image, boolean isSelect, int selectCount);
    }

    public interface OnItemClickListener {
        void OnItemClick(Image image, int position);

        void OnCameraClick();
    }

    private void maskProcessing(ViewHolder viewHolder, Image image) {

        boolean b = mSelectImages.size() >= mMaxCount;
        boolean contains = mSelectImages.contains(image);
        if (!contains && b) {
            viewHolder.ivImage.setColorFilter(ContextCompat.getColor
                    (mContext, R.color.rximage_color_half_white), PorterDuff.Mode.SRC_ATOP);
            Log.i("maskProcessing","11111111");
        } else {
            viewHolder.ivImage.setColorFilter(ContextCompat.getColor
                    (mContext, R.color.rximage_color_20), PorterDuff.Mode.SRC_ATOP);
            Log.i("maskProcessing","22222222");
        }
    }

    /**
     * Tips
     */
    private void showPromptDialog(String content) {
        PictureCustomDialog dialog = new PictureCustomDialog(mContext, R.layout.rximage_prompt_dialog);
        TextView btnOk = dialog.findViewById(R.id.btnOk);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        tvContent.setText(content);
        btnOk.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
