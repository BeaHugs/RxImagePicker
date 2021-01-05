package org.beahugs.imagepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donkingliang.imageselector.R;
import com.basics.videoplay.view.VideoPlayer;

import java.io.File;


/**
 * @ClassName: VideoPlayActivity
 * @Author: wangyibo
 * @CreateDate: 2020/3/8 14:28
 * @Version: 1.0
 */
public class VideoPlayActivity extends AppCompatActivity {

    private VideoPlayer video_view;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_play);

        video_view = findViewById(R.id.video_view);
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("videoUrl");
        Log.i("videoUrl", videoUrl);
        //video_view.start();
        video_view.setTitle(new File(videoUrl).getName());
        video_view.loadAndStartVideo(this, videoUrl);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("forceOrientation","onConfigurationChanged");
        if (video_view != null) {
            video_view.updateActivityOrientation();
        }
    }


    public static void start(Context context, String url) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("videoUrl", url);
        context.startActivity(intent);
    }

}
