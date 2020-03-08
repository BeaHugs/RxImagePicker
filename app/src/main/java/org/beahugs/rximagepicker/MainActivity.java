package org.beahugs.rximagepicker;

import android.Manifest;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rvImage = findViewById(R.id.rv_image);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        findViewById(R.id.tv_moment_list_add).setOnClickListener(this);
        findViewById(R.id.open_dialog).setOnClickListener(this);

        add_single = findViewById(R.id.add_single);
        add_camera = findViewById(R.id.add_camera);
        add_crop = findViewById(R.id.add_crop);
        add_preview = findViewById(R.id.add_preview);

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





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_moment_list_add:
                //单选
                ImageSelector.builder()
                        .useCamera(add_camera.isChecked()) // 使用拍照
                        .setCrop(add_crop.isChecked())  // 使用图片剪切
                        //.setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(add_single.isChecked())  //设置是否单选
                        .canPreview(add_preview.isChecked()) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9)//如果设置大于0
                        .setFileType(0)
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            case R.id.open_dialog:
               // startActivity(new Intent(this, DiaLogActivity.class));
                Toast.makeText(this,"注释了...",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
