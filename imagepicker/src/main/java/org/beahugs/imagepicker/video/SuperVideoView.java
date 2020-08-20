package org.beahugs.imagepicker.video;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.donkingliang.imageselector.R;

/**
 * Created by Song on 2017/4/25.
 */
public class SuperVideoView extends RelativeLayout {

    private CustomVideoView videoView;
    private SeekBar seekbarProgress;
    private ImageView btnController;
    private TextView tvCurrentProgress;
    private TextView tvTotalProgress;
    private ImageView ivVolume;
    private SeekBar seekbarVolume;
    private ImageView btnScreen;
    private FrameLayout flVolume;
    private FrameLayout flLight;
    private RelativeLayout llyController;
    private RelativeLayout rlContainer;
    private AudioManager mAudioManager;
    private int screenWidth;
    private int screenHeight;
    private Context mContext;
    private View videoLayout;
    private Activity mActivity;
    private int videoPos;
    private int state = 0;
    private String mVideoPath;
    private boolean isVerticalScreen = true;
    private static final int UPDATE_PROGRESS = 1;

    public SuperVideoView(Context context) {
        super(context, null);
    }

    public SuperVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        initView();
        initData();
        initListener();
    }

    public void register(Activity activity){
        this.mActivity = activity;
    }

    /**
     * 设置媒体路径
     * @param path
     */
    public void setVideoPath(String path) {

        this.mVideoPath = path;
        if(path.startsWith("http") || path.startsWith("https")) {
            videoView.setVideoURI(Uri.parse(path));
        } else {
            videoView.setVideoPath(mVideoPath);
        }
    }

    private void init() {
        screenWidth = ScreenUtils.getScreenWidth(mContext);
        screenHeight = ScreenUtils.getScreenHeight(mContext);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        mContext.registerReceiver(new MyVolumeReceiver(),filter);
    }

    /**
     * UI 初始化
     */
    private void initView() {

        videoLayout = LayoutInflater.from(mContext).inflate(R.layout.video_layout, this, true);
        flVolume = (FrameLayout) videoLayout.findViewById(R.id.fl_volume);
        flLight = (FrameLayout) videoLayout.findViewById(R.id.fl_light);
        videoView = (CustomVideoView) videoLayout.findViewById(R.id.videoView);
        seekbarProgress = (SeekBar) videoLayout.findViewById(R.id.seekbar_progress);
        seekbarVolume = (SeekBar) videoLayout.findViewById(R.id.seekbar_volume);
        btnController = (ImageView) videoLayout.findViewById(R.id.btn_controller);
        btnScreen = (ImageView) videoLayout.findViewById(R.id.btn_screen);
        tvCurrentProgress = (TextView) videoLayout.findViewById(R.id.tv_currentProgress);
        tvTotalProgress = (TextView) videoLayout.findViewById(R.id.tv_totalProgress);
        ivVolume = (ImageView) videoLayout.findViewById(R.id.iv_volume);
        llyController = (RelativeLayout) videoLayout.findViewById(R.id.lly_controller);
        rlContainer = (RelativeLayout) videoLayout.findViewById(R.id.rl_container);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekbarVolume.setProgress(currentVolume);
    }

    /**
     * 注册事件
     */
    private void initListener() {

        btnScreen.setOnClickListener(new OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View v) {
                if (isVerticalScreen) {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        btnController.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    btnController.setImageResource(R.drawable.btn_play_style);
                    videoView.pause();
                    mHandler.removeMessages(UPDATE_PROGRESS);
                } else {
                    btnController.setImageResource(R.drawable.btn_pause_style);
                    videoView.start();
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                    if (state == 0) state = 1;
                }
            }
        });

        /**
         * 状态事件
         */
        videoView.setStateListener(new CustomVideoView.StateListener() {

            /**
             * 改变声音大小
             */
            @Override
            public void changeVolumn(float detlaY) {

                if(flVolume.getVisibility() == View.GONE){
                    flVolume.setVisibility(View.VISIBLE);
                }
                int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                int index = (int)(detlaY / screenHeight * maxVolume * 3);
                int volume = Math.max(0,currentVolume - index);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
                seekbarVolume.setProgress(volume);
            }

            /**
             * 改变屏幕亮度
             * @param detlaX
             */
            @Override
            public void changeBrightness(float detlaX) {

                if(flLight.getVisibility() == View.GONE){
                    flLight.setVisibility(View.VISIBLE);
                }
                WindowManager.LayoutParams wml = mActivity.getWindow().getAttributes();
                float screenBrightness =  wml.screenBrightness;
                float index = detlaX / screenWidth / 3;
                screenBrightness+=index;
                if(screenBrightness > 1.0f){
                    screenBrightness = 1.0f;
                } else if(screenBrightness < 0.01f){
                    screenBrightness = 0.01f;
                }
                wml.screenBrightness = screenBrightness;
                mActivity.getWindow().setAttributes(wml);
            }

            @Override
            public void hideHint() {
                if(flLight.getVisibility() == View.VISIBLE) {
                    flLight.setVisibility(GONE);
                }

                if(flVolume.getVisibility() == View.VISIBLE) {
                    flVolume.setVisibility(GONE);
                }
            }
        });

        /**
         * 声音控制
         */
        seekbarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        /**
         * 播放进度
         */
        seekbarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextViewFormat(tvCurrentProgress, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 暂停刷新
                mHandler.removeMessages(UPDATE_PROGRESS);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (state != 0) {
                    mHandler.sendEmptyMessage(UPDATE_PROGRESS);
                }
                videoView.seekTo(seekBar.getProgress());
            }
        });
    }

    /**
     * 屏幕状态改变
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isVerticalScreen = true;
            ivVolume.setVisibility(View.GONE);
            seekbarVolume.setVisibility(View.GONE);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtils.dipToPx(mContext,290));
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            isVerticalScreen = false;
            ivVolume.setVisibility(View.VISIBLE);
            seekbarVolume.setVisibility(View.VISIBLE);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            // 移除半屏状态
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 切换尺寸
     * @param width
     * @param height
     */
    public void setVideoViewScale(int width, int height) {

        ViewGroup.LayoutParams videoViewLayoutParams = videoView.getLayoutParams();
        videoViewLayoutParams.width = width;
        videoViewLayoutParams.height = height;
        videoView.setLayoutParams(videoViewLayoutParams);

        RelativeLayout.LayoutParams rlContainerLayoutParams = (RelativeLayout.LayoutParams) rlContainer.getLayoutParams();
        rlContainerLayoutParams.width = width;
        rlContainerLayoutParams.height = height;
        rlContainer.setLayoutParams(rlContainerLayoutParams);
    }

    /**
     * 格式化时间进度
     */
    private void updateTextViewFormat(TextView tv, int m) {

        String result;
        // 毫秒转成秒
        int second = m / 1000;
        int hour = second / 3600;
        int minute = second % 3600 / 60;
        int ss = second % 60;

        if (hour != 0) {
            result = String.format("%02d:%02d:%02d", hour, minute, ss);
        } else {
            result = String.format("%02d:%02d", minute, ss);
        }
        tv.setText(result);
    }

    private Handler mHandler = new  Handler(){

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == UPDATE_PROGRESS) {

                // 获取当前时间
                int currentTime = videoView.getCurrentPosition();
                // 获取总时间
                int totalTime = videoView.getDuration() - 100;
                if (currentTime >= totalTime) {
                    videoView.pause();
                    videoView.seekTo(0);
                    seekbarProgress.setProgress(0);
                    btnController.setImageResource(R.drawable.btn_play_style);
                    updateTextViewFormat(tvCurrentProgress, 0);
                    mHandler.removeMessages(UPDATE_PROGRESS);
                } else {
                    seekbarProgress.setMax(totalTime);
                    seekbarProgress.setProgress(currentTime);
                    updateTextViewFormat(tvCurrentProgress, currentTime);
                    updateTextViewFormat(tvTotalProgress, totalTime);
                    mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
                }
            }
        }
    };

    public void onPause() {
        videoPos = videoView.getCurrentPosition();
        videoView.stopPlayback();
        mHandler.removeMessages(UPDATE_PROGRESS);
    }

    public void onResume() {
        videoView.seekTo(videoPos);
        videoView.resume();
    }

    /**
     * 设置播放进度条样式
     * @param drawable
     */
    public void setProgressBg(Drawable drawable) {
        seekbarProgress.setProgressDrawable(drawable);
    }

    /**
     * 设置声音控制条样式
     * @param drawable
     */
    public void setVolumeBg(Drawable drawable){
        seekbarVolume.setProgressDrawable(drawable);
    }

    class MyVolumeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                // 当前的媒体音量
                int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                seekbarVolume.setProgress(currentVolume);
            }
        }
    }
}
