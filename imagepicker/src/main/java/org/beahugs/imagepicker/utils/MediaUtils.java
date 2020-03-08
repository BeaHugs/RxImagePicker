package org.beahugs.imagepicker.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

/**
 * @ClassName: MediaUtils
 * @Author: wangyibo
 * @CreateDate: 2020/3/8 14:10
 * @Version: 1.0
 */
public class MediaUtils {
    /**
     * 获取视频时长
     *
     * @param context
     * @param isAndroidQ
     * @return
     */
    public static long extractDuration(Context context, boolean isAndroidQ, String path) {
        return isAndroidQ ? getLocalDuration(context, Uri.parse(path))
                : getLocalDuration(path);
    }

    private static long getLocalDuration(Context context, Uri uri) {
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(context, uri);
            return Long.parseLong(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static long getLocalDuration(String path) {
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            return Long.parseLong(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
