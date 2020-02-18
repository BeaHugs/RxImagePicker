package org.beahugs.rxpopup;

/**
 * @ClassName: AnimAction
 * @Author: wangyibo
 * @CreateDate: 2020/2/16 22:49
 * @Version: 1.0
 */
public interface AnimAction {

    /** 没有动画效果 */
    int NO_ANIM = 0;

    /** 默认动画效果 */
    int DEFAULT = R.style.ScaleAnimStyle;

    /** 缩放动画 */
    int SCALE = R.style.ScaleAnimStyle;

    /** IOS 动画 */
    int IOS = R.style.IOSAnimStyle;

    /** 吐司动画 */
    int TOAST = android.R.style.Animation_Toast;

    /** 顶部弹出动画 */
    int TOP = R.style.TopAnimStyle;

    /** 底部弹出动画 */
    int BOTTOM = R.style.BottomAnimStyle;

    /** 左边弹出动画 */
    int LEFT = R.style.LeftAnimStyle;

    /** 右边弹出动画 */
    int RIGHT = R.style.RightAnimStyle;
}
