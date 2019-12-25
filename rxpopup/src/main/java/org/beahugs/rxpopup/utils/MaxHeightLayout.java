package org.beahugs.rxpopup.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class MaxHeightLayout extends RelativeLayout {

    private int maxHeight;

    public MaxHeightLayout(Context context) {
        super(context);
        init(context);
    }

    public MaxHeightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaxHeightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        maxHeight = (int) (getScreenHeight(context) * 0.8);
    }

    public MaxHeightLayout setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        heightSize = heightSize <= maxHeight ? heightSize : maxHeight;
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
