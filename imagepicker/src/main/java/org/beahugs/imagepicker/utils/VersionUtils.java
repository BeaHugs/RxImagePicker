package org.beahugs.imagepicker.utils;

import android.os.Build;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class VersionUtils {

    /**
     * 判断是否是Android L版本
     *
     * @return
     */
    public static boolean isAndroidL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 判断是否是Android N版本
     *
     * @return
     */
    public static boolean isAndroidN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 判断是否是Android P版本
     *
     * @return
     */
    public static boolean isAndroidP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断是否是Android Q版本
     *
     * @return
     */
    public static boolean isAndroidQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }
}
