package com.basics.ui.widget.statusview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.basics.ui.R;


public interface StatusAction {

    /**
     * 获取提示布局
     */
    HintLayout getHintLayout();

    /**
     * 显示加载中
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showLoading() {
        showLoading(R.raw.loading);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showLoading(@RawRes int id) {
        HintLayout layout = getHintLayout();
        layout.show();
        layout.setAnim(id);
        layout.setHint("");
        layout.setOnClickListener(null);
    }

    /**
     * 显示加载完成
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showComplete() {
        HintLayout layout = getHintLayout();
        if (layout != null && layout.isShow()) {
            layout.hide();
        }
    }

    /**
     * 显示空提示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showEmpty() {
        showLayout(R.drawable.hint_empty_ic, R.string.hint_layout_no_data, null);
    }

    /**
     * 显示错误提示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showError(View.OnClickListener listener) {
        HintLayout layout = getHintLayout();
        Context context = layout.getContext();
        ConnectivityManager manager = ContextCompat.getSystemService(context, ConnectivityManager.class);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            // 判断网络是否连接
            if (info == null || !info.isConnected()) {
                showLayout(R.drawable.hint_nerwork_ic, R.string.hint_layout_error_network, listener);
                return;
            }
        }
        showLayout(R.drawable.hint_error_ic, R.string.hint_layout_error_request, listener);
    }

    /**
     * 显示自定义提示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showLayout(@DrawableRes int drawableId, @StringRes int stringId, View.OnClickListener listener) {
        HintLayout layout = getHintLayout();
        Context context = layout.getContext();
        showLayout(ContextCompat.getDrawable(context, drawableId), context.getString(stringId), listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    default void showLayout(Drawable drawable, CharSequence hint, View.OnClickListener listener) {
        HintLayout layout = getHintLayout();
        layout.show();
        layout.setIcon(drawable);
        layout.setHint(hint);
        layout.setOnClickListener(listener);
    }
}