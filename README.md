# RxImagePicker
Android图片选择器、支持AndroidX，支持图片的单选、多选、图片预览、图片文件夹切换、相机拍照、图片裁剪

**V1.0**

- 支持通过相机拍照获取图片
- 支持从相册选择图片
- 支持从文件选择图片  
- 支持批量图片选取
- 支持图片裁切
- 支持自动权限管理(无需关心SD卡及摄像头权限等问题) 
- 支持AndroidX


```xml
//储存卡的读写权限
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//调用相机权限
<uses-permission android:name="android.permission.CAMERA" />


**3、调起图片选择器**

     //使用方法
     ImageSelector.builder()
                        .useCamera(add_camera.isChecked()) // 使用拍照
                        .setCrop(add_crop.isChecked())  // 使用图片剪切
                        //.setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(add_single.isChecked())  //设置是否单选
                        .canPreview(add_preview.isChecked()) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9)//如果设置大于0
                        .start(this, REQUEST_CODE); // 打开相册
