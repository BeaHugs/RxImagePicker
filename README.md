# RxImagePicker
Android图片选择器、支持AndroidX，支持图片的单选、多选、图片预览、视频预览、图片文件夹切换、相机拍照、图片裁剪
### 项目介绍:
[网络请求框架--我的另一个开源项目: https://github.com/BeaHugs/rxhttp](https://github.com/BeaHugs/rxhttp)

## 效果图
| 文件夹 | 带相机 | 相册详情(可以缩放) | 演示 |
| ------------ | ------------- | ------------ | ------------- |
| ![相册](https://github.com/BeaHugs/RxImagePicker/blob/master/image/Screenshot_20191212-144442.jpg) | ![文件夹](https://github.com/BeaHugs/RxImagePicker/blob/master/image/Screenshot_20191212-144501.png)  | ![相册](https://github.com/BeaHugs/RxImagePicker/blob/master/image/Screenshot_20191212-144522.png) | ![文件夹](https://github.com/BeaHugs/RxImagePicker/blob/master/image/Screenshot_20191212-144541.png) |


**V1.0**
- 对您提出的问题可以熬夜修改
- 支持通过相机拍照获取图片
- 支持从相册选择图片
- 支持从文件选择图片  
- 支持批量图片选取
- 支持图片裁切
- 支持自动权限管理(无需关心SD卡及摄像头权限等问题) 
- 支持AndroidX

**2、权限配置**

   ```xml
   //储存卡的读写权限
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
   //调用相机权限
   <uses-permission android:name="android.permission.CAMERA" />


<!-- Android 7.0 文件共享配置，1.7.0之前必须配置，1.7.0后不需要 -->
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>

在res/xml文件夹下创建file_paths.xml文件(名字可以自己定义)
<?xml version="1.0" encoding="utf-8"?>
<paths>

    <!-- 这个是保存拍照图片的路径,必须配置。 -->
    <external-path
        name="images"
        path="Pictures" />
</paths>
```


**3、代码调用 几行代码**

```
    //java
    //使用方法
    ImageSelector.builder()
                        .useCamera(add_camera.isChecked()) // 使用拍照
                        .setCrop(add_crop.isChecked())  // 使用图片剪切
                        //.setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(add_single.isChecked())  //设置是否单选
                        .canPreview(add_preview.isChecked()) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9)//如果设置大于0
                        .start(this, REQUEST_CODE); // 打开相册
                        
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //数据回传
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
        }
    }
    
```
**4、Apk下载体验**

[fir下载地址](暂无 更新中)



**5、Android开发交流群**  


| Android开发交流群 |  
| ------------ | 
| ![相册](https://github.com/BeaHugs/RxImagePicker/blob/master/image/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20200307144007.jpg) | 


     
     
     

