package com.wyb.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.wyb.baseui.R;


public final class ScaleImageView extends AppCompatImageView {

    private float mScaleSize = 1.2f;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        setScaleSize(array.getFloat(R.styleable.ScaleImageView_scaleRatio, mScaleSize));
        array.recycle();
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        // 判断当前手指是否按下了
        if (pressed) {
            setScaleX(mScaleSize);
            setScaleY(mScaleSize);
        } else {
            setScaleX(1);
            setScaleY(1);
        }
    }

    public void setScaleSize(float size) {
        mScaleSize = size;
    }
}