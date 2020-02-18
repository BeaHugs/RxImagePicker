package org.beahugs.rxpopup.base;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @ClassName: ClickAction
 * @Author: wangyibo
 * @CreateDate: 2020/2/16 23:00
 * @Version: 1.0
 */
public interface ClickAction extends View.OnClickListener {

    <V extends View> V findViewById(@IdRes int id);

    @Override
    default void onClick(View v) {
        // 默认不实现，让子类实现
    }

    default void setOnClickListener(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }
}
