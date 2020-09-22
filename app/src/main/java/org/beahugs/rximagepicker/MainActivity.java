package org.beahugs.rximagepicker;

import android.Manifest;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.CheckBox;

import org.beahugs.imagepicker.permission.OnPerCallBack;
import org.beahugs.imagepicker.permission.RxPermission;

import org.beahugs.imagepicker.utils.ImageSelector;

import org.beahugs.rximagepicker.adapter.ImageAdapter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;

    private RecyclerView rvImage;
    private ImageAdapter mAdapter;
    private CheckBox add_single;
    private CheckBox add_camera;
    private CheckBox add_crop,add_preview;
    private CheckBox imageOrVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








       rvImage = findViewById(R.id.rv_image);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        findViewById(R.id.tv_moment_list_add).setOnClickListener(this);

        add_single = findViewById(R.id.add_single);
        add_camera = findViewById(R.id.add_camera);
        imageOrVideo = findViewById(R.id.imageOrVideo);



        RxPermission.with(this).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new OnPerCallBack() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        // 预加载手机图片。加载图片前，请确保app有读取储存卡权限
                        //ImageSelector.preload(MainActivity.this);
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            mAdapter.refresh(images);
        }
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_moment_list_add:


//                ImageSelectActivity.start(this, new ImageSelectActivity.OnPhotoSelectListener() {
//                    @Override
//                    public void onSelected(List<String> data) {
//
//                    }
//                });

                //单选
                ImageSelector.builder()
                        .useCamera(add_camera.isChecked()) // 使用拍照
                        .setSingle(add_single.isChecked())  //设置是否单选
                        .setSelectImageOrVideo(imageOrVideo.isChecked())//可以选择图片和视频
                        .setMaxSelectCount(2)//如果设置大于0
                        .setFileType(0)
                        .start(this, REQUEST_CODE); // 打开相册
                break;

        }
    }

}
