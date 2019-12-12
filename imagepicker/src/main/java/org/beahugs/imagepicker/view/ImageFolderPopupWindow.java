package org.beahugs.imagepicker.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.R;

import org.beahugs.imagepicker.adapter.FolderAdapter;
import org.beahugs.imagepicker.entry.Folder;
import org.beahugs.imagepicker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class ImageFolderPopupWindow extends PopupWindow {
    private static final int DEFAULT_IMAGE_FOLDER_SELECT = 0;//默认选中文件夹

    public static final int ANIM_DURATION = 300;

    private Context mContext;
    private ArrayList<Folder> mMediaFolderList;

    private RecyclerView mRecyclerView;
    private FolderAdapter mImageFoldersAdapter;
    private PoPupWindowOutsideImpl poPupWindowOutsideImpl;

    public ImageFolderPopupWindow(Context context, ArrayList<Folder> mediaFolderList) {
        this.mContext = context;
        this.mMediaFolderList = mediaFolderList;
        initView();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.window_image_folders, null);
        mRecyclerView = view.findViewById(R.id.rv_main_imageFolders);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mImageFoldersAdapter = new FolderAdapter(mContext, mMediaFolderList);
        mRecyclerView.setAdapter(mImageFoldersAdapter);

        initPopupWindow(view);
    }

    /**
     * 初始化PopupWindow的一些属性
     */
    private void initPopupWindow(View view) {
        setContentView(view);
        int[] screenSize = Utils.getScreenSize(mContext);
        setWidth(screenSize[0]);
        setHeight((int) (screenSize[1] * 0.6));
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.i("poPupWindowOutsideImpl", "poPupWindowOutsideImpl: ");
                    dismiss();
                }
                return false;
            }
        });
    }


    public interface PoPupWindowOutsideImpl{
        void outsideDismiss();
    }

    public void setPoPupWindowOutsideImpl(PoPupWindowOutsideImpl poPupWindowOutsideImpl){

        this.poPupWindowOutsideImpl = poPupWindowOutsideImpl;
    }

    public FolderAdapter getAdapter() {
        return mImageFoldersAdapter;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (poPupWindowOutsideImpl!=null){
            poPupWindowOutsideImpl.outsideDismiss();
        }
    }
}
