package org.beahugs.rxpopup.base;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * @ClassName: HandlerAction
 * @Author: wangyibo
 * @CreateDate: 2020/2/16 23:09
 * @Version: 1.0
 */
public interface HandlerAction {
    Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 获取 Handler
     */
    default Handler getHandler() {
        return HANDLER;
    }

    /**
     * 延迟执行
     */
    default boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    default boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    default boolean postAtTime(Runnable r, long uptimeMillis) {
        // 发送和这个 Activity 相关的消息回调
        return HANDLER.postAtTime(r, this, uptimeMillis);
    }

    /**
     * 移除消息回调
     */
    default void removeCallbacks() {
        HANDLER.removeCallbacksAndMessages(this);
    }
}
