package com.wyb.videoplay.constant;

/**
 * 进度条拖动状态
 */
public interface SeekBarState {
    /**
     * 用户开始触摸
     */
    int START_TRACKING = 0X01;
    /**
     * 拖拽过程中
     */
    int IN_PROGRESS = 0X02;
    /**
     * 用户结束拖拽
     */
    int STOP_TRACKING = 0X03;

}
