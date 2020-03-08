package org.beahugs.imagepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donkingliang.imageselector.R;

/**
 * @ClassName: VideoPlayActivity
 * @Author: wangyibo
 * @CreateDate: 2020/3/8 14:28
 * @Version: 1.0
 */
public class VideoPlayActivity extends AppCompatActivity {

    private VideoView video_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        video_view = findViewById(R.id.video_view);
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("videoUrl");
        video_view.setVideoPath(videoUrl);
        video_view.start();


    }



    public static void start(Context context,String url){
        Intent intent = new Intent(context,VideoPlayActivity.class);
        intent.putExtra("videoUrl",url);
        context.startActivity(intent);
    }

}
