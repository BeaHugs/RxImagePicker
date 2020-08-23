package com.wyb.videoplay.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
/**
 * 网络管理
 */
public class NetworkUtil {
    /**
     * 网络是否可用
     *
     * @return true-有网络
     */
    public static boolean isNetworkAvailable(@NonNull Context cxt) {
        ConnectivityManager manager = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean connected = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            connected = true;
        }
        return connected;
    }
}
