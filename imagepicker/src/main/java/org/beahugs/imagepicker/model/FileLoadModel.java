package org.beahugs.imagepicker.model;

import android.provider.MediaStore;

import java.util.Locale;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class FileLoadModel {
    /**
     * 全部模式下条件
     */
    public static String getAllFile(String time_condition) {
        //(media_type=? AND mime_type!='image/gif' OR media_type=? AND 0 < duration and duration <= 9223372036854775807) AND _size>0
        String condition = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                + " OR "
                + (MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + time_condition) + ")"
                + " AND " + MediaStore.MediaColumns.SIZE + ">0";
        return condition;
    }


    /**
     * 视频时长
     * @param exMaxLimit
     * @param exMinLimit
     * @return
     */
    public static String getDurationCondition(long exMaxLimit, long exMinLimit) {
        long maxS =  Long.MAX_VALUE ;
        if (exMaxLimit != 0) {
            maxS = Math.min(maxS, exMaxLimit);
        }
        return String.format(Locale.CHINA, "%d <%s " + MediaStore.MediaColumns.DURATION + " and " + MediaStore.MediaColumns.DURATION + " <= %d",
                Math.max(exMinLimit, 0),
                Math.max(exMinLimit, 0) == 0 ? "" : "=",
                maxS);
    }

}
