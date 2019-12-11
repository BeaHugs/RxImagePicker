package org.beahugs.imagepicker.utils;
/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class StringUtils {

    public static boolean isNotEmptyString(final String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEmptyString(final String str) {
        return str == null || str.length() <= 0;
    }
}
