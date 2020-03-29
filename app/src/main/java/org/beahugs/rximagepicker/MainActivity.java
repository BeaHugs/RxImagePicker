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
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                myclick2();
                Toast.makeText(this,"注释了...",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void myclick2(){

        final String md5Str = "65206e36e540ef69fe205624e4d7a69f";
        try {
            //创建data json对象

            /**
             *          json = {
             *               'SIGN_STUDENTS':'[{"COURSE_SIGN.STUDENT_ID":1000,"COURSE_SIGN.ADD_DATATIME":"2019-11-03 03:00:00"},{"COURSE_SIGN.STUDENT_ID":1002,"COURSE_SIGN.ADD_DATATIME":"2019-11-03 03:00:00"}]',
             *               'MD5_CODE':a
             *         }
             */
            final String  myjson = "json = {'SIGN_STUDENTS':[{'COURSE_SIGN.STUDENT_ID':1000,'COURSE_SIGN.ADD_DATATIME':'2019-11-03 03:00:00'},{'COURSE_SIGN.STUDENT_ID':1002,'COURSE_SIGN.ADD_DATATIME':'2019-11-03 03:00:00'}],'MD5_CODE':'"+md5Str+"'}";

            //json对象中放入参数MD5_CODE

            // 访问url保存考勤记录
            new Thread(){
                @Override
                public void run() {
                    try {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                            }
                        });

                        String POST_STU_URL = "http://dg.dev.boogcloud.com/dg/bubu/course/sign/batchSignStudents";

                        //发送post请求
                        URL url = new URL(POST_STU_URL);
                        HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("Content-Length",myjson.length()+"");
                        conn.setUseCaches(false);
                        conn.setDoOutput(true);
                        //conn.setDoInput(true);
                        OutputStream os = conn.getOutputStream();
                        //os.write(myjson.getBytes());
                        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                        out.append(myjson);
                        //os.write(URLEncoder.encode(myjson,"utf-8").getBytes());
                        //os.write(URLEncoder.encode(data.toString(),"utf-8").getBytes());
                        os.flush();
                        os.close();
                        //获取响应码
                        int code = conn.getResponseCode();

                        //当响应码==200时
                        if (code==200) {
                            //当响应码==200时,从响应流中读取二进制数据
                            InputStream is = conn.getInputStream();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            int len = 0;
                            byte[] buffer = new byte[1024];
                            while ((len = is.read(buffer)) != -1) {
                                baos.write(buffer, 0, len);
                            }
                            is.close();
                            byte[] result = baos.toByteArray();
                            //将二进制数据转为字符串
                            String strResult = new String(result);

                            //json字符串转为json对象
                            JSONObject jsonObj = new JSONObject(strResult);
                            //从json对象中取数据
                            final String msg = jsonObj.getString("msg");
                            int mycode = jsonObj.getInt("code");
                            //JSONArray data = jsonObj.getJSONArray("data");
                            //JSONObject extraData = jsonObj.getJSONObject("extraData");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                                }
                            });
                            //当响应码!=200时,toast提示
                        }else {
                            final String toastText = "远程保存考勤记录失败1111";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,toastText,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (Exception e) {
                        // Toast.makeText(this,"获取数据失败",10).show();
                        e.printStackTrace();
                        final String a = e.getMessage();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * 32位MD5加密
     * @param content : 待加密内容
     * @return
     */

    private String md5(String content){
        byte[]hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        }

        StringBuilder hex = new StringBuilder(hash.length*2);
        for(byte b:hash){
            if((b & 0xFF)< 0x10){
                hex.append(0);
            }
            hex.append(Integer.toHexString(b & 0xff));
        }

        return hex.toString();
    }


}
