package com.ui.timeline.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ui.timeline.R;

/**
 * Created by lin18 on 2017/8/23.
 */
public class MainTimeLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_main);
    }

    public void timeline(View view) {
        startActivity(new Intent(this, TimeLineActivity.class));
    }

    public void order(View view) {
        startActivity(new Intent(this, OrderActivity.class));
    }

    public void trace(View view) {
        startActivity(new Intent(this, TraceActivity.class));
    }
}
