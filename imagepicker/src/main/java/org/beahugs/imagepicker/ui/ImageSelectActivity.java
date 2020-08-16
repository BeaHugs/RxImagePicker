package org.beahugs.imagepicker.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wyb.baseui.widget.statusview.HintLayout;
import com.wyb.baseui.widget.statusview.StatusAction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.core.os.HandlerCompat.postDelayed;

public final class ImageSelectActivity extends AppCompatActivity
        implements View.OnClickListener,
        BaseAdapter.OnChildClickListener,
        BaseAdapter.OnItemClickListener,
        BaseAdapter.OnItemLongClickListener,
        StatusAction {


    public static final String AMOUNT = "amount";

    public static final String IMAGE = "picture";

    private static final int REQUEST_CODE = 0x00000011;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void start(Activity activity, OnPhotoSelectListener listener) {
        start(activity, 1, listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void start(Activity activity, int maxSelect, OnPhotoSelectListener listener) {
        if (maxSelect < 1) {
            // 最少要选择一个图片
            throw new IllegalArgumentException("Choose at least one image");
        }
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        intent.putExtra(AMOUNT, maxSelect);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }


    private HintLayout mHintLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingView;

    private ImageSelectAdapter mAdapter;

    /**
     * 最大选中
     */
    private int mMaxSelect = 1;
    /**
     * 选中列表
     */
    private final ArrayList<String> mSelectImage = new ArrayList<>();

    /**
     * 全部图片
     */
    private final ArrayList<String> mAllImage = new ArrayList<>();
    /**
     * 图片专辑
     */
    private final HashMap<String, List<String>> mAllAlbum = new HashMap<>();


    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_select_activity);

        context = this;

        mHintLayout = findViewById(R.id.hl_image_select_hint);
        mRecyclerView = findViewById(R.id.rv_image_select_list);
        mFloatingView = findViewById(R.id.fab_image_select_floating);
        mFloatingView.setOnClickListener(this);

        mAdapter = new ImageSelectAdapter(this, mSelectImage);
        mAdapter.setOnChildClickListener(R.id.fl_image_select_check, this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        // 禁用动画效果
        mRecyclerView.setItemAnimator(null);
        // 添加分割线
        mRecyclerView.addItemDecoration(new GridSpaceDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics())));


        initData();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void initData() {
        // 获取最大的选择数
        mMaxSelect = getIntent().getIntExtra(AMOUNT, mMaxSelect);

        // 显示加载进度条
        showLoading();
        // 加载图片列表
        new Thread(new Runnable() {
            @Override
            public void run() {

                mAllAlbum.clear();
                mAllImage.clear();

                final Uri contentUri = MediaStore.Files.getContentUri("external");
                final String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC";
                final String selection = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)" + " AND " + MediaStore.MediaColumns.SIZE + ">0";

                ContentResolver contentResolver = getContentResolver();
                String[] projections = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.DATA,
                        MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED,
                        MediaStore.MediaColumns.MIME_TYPE, MediaStore.MediaColumns.WIDTH,
                        MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.SIZE};

                Cursor cursor = null;

                cursor = contentResolver.query(contentUri, projections, selection, new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)}, sortOrder);

                if (cursor != null && cursor.moveToFirst()) {

                    int pathIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                    int mimeTypeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE);
                    int sizeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE);

                    do {
                        long size = cursor.getLong(sizeIndex);
                        // 图片大小不得小于 10 KB
                        if (size < 1024 * 10) {
                            continue;
                        }

                        String type = cursor.getString(mimeTypeIndex);
                        String path = cursor.getString(pathIndex);
                        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(type)) {
                            continue;
                        }

                        File file = new File(path);
                        if (!file.exists() || !file.isFile()) {
                            continue;
                        }

                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            // 获取目录名作为专辑名称
                            String albumName = parentFile.getName();
                            List<String> data = mAllAlbum.get(albumName);
                            if (data == null) {
                                data = new ArrayList<>();
                                mAllAlbum.put(albumName, data);
                            }
                            data.add(path);
                            mAllImage.add(path);
                        }

                    } while (cursor.moveToNext());

                    cursor.close();
                }

                handler.postAtTime(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerView.scrollToPosition(0);
                        // 设置新的列表数据
                        mAdapter.setData(mAllImage);

                        if (mSelectImage.isEmpty()) {
                            mFloatingView.setImageResource(R.drawable.camera_ic);
                        } else {
                            mFloatingView.setImageResource(R.drawable.succeed_ic);
                        }

                        // 执行列表动画
                        mRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(context, R.anim.fall_down_layout));
                        mRecyclerView.scheduleLayoutAnimation();

                        // 设置右标提

                        if (mAllImage.isEmpty()) {
                            // 显示空布局
                            showEmpty();
                        } else {
                            // 显示加载完成
                            showComplete();
                        }

                    }
                }, 500);

            }
        }).start();
    }


    private Handler handler = new Handler();


    @Override
    public void onClick(View v) {



    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {


        if (childView.getId() == R.id.fl_image_select_check) {
            if (mSelectImage.contains(mAdapter.getItem(position))) {
                mSelectImage.remove(mAdapter.getItem(position));

                if (mSelectImage.isEmpty()) {
                    mFloatingView.hide();
                    //postDelayed(() -> {
                        mFloatingView.setImageResource(R.drawable.camera_ic);
                        mFloatingView.show();
                    //}, 200);
                }

            } else {

                if (mMaxSelect == 1 && mSelectImage.size() == 1) {

                    List<String> data = mAdapter.getData();
                    if (data != null) {
                        int index = data.indexOf(mSelectImage.get(0));
                        if (index != -1) {
                            mSelectImage.remove(0);
                            mAdapter.notifyItemChanged(index);
                        }
                    }
                    mSelectImage.add(mAdapter.getItem(position));

                } else if (mSelectImage.size() < mMaxSelect) {

                    mSelectImage.add(mAdapter.getItem(position));

                    if (mSelectImage.size() == 1) {
                        mFloatingView.hide();
                       // postDelayed(() -> {
                            mFloatingView.setImageResource(R.drawable.succeed_ic);
                            mFloatingView.show();
                        //}, 200);
                    }
                } else {
                    //toast(String.format(getString(R.string.image_select_max_hint), mMaxSelect));
                }
            }
            mAdapter.notifyItemChanged(position);
        }


    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

        if (mSelectImage.contains(mAdapter.getItem(position))) {
           // ImagePreviewActivity.start(getActivity(), mSelectImage, mSelectImage.indexOf(mAdapter.getItem(position)));
        } else {
            //ImagePreviewActivity.start(getActivity(), mAdapter.getItem(position));
        }

    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
        return false;
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }


    /**
     * 图片选择监听
     */
    public interface OnPhotoSelectListener {

        /**
         * 选择回调
         *
         * @param data 图片列表
         */
        void onSelected(List<String> data);

        /**
         * 取消回调
         */
        @RequiresApi(api = Build.VERSION_CODES.N)
        default void onCancel() {
        }
    }
}
