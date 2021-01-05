package com.basics.videoplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


import androidx.appcompat.widget.AppCompatSeekBar;


public class CustomSeekBar extends AppCompatSeekBar {
    private boolean mSeekable = true;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setSeekable(boolean seekable) {
        this.mSeekable = seekable;
    }

    public boolean isSeekable() {
        return mSeekable;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return !mSeekable ? false : super.onTouchEvent(event);
    }
}
