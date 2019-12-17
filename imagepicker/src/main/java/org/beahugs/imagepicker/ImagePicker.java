package org.beahugs.imagepicker;



/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class ImagePicker {
    public static String DEFAULT_FILE_NAME = "imagePicker";
    //选择返回的key
    public static final String INTENT_KEY_PICKER_RESULT = "pickerResult";
    //选择返回code
    public static final int REQ_PICKER_RESULT_CODE = 1433;
    //拍照返回码、拍照权限码
    public static final int REQ_CAMERA = 1431;
    //存储权限码
    public static final int REQ_STORAGE = 1432;

    /**
     * 是否选中原图
     */
    public static boolean isOriginalImage = false;


    public static String REQUEST_CODE ="image_callBack";


    public static final int TYPE_ALL = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;




}
