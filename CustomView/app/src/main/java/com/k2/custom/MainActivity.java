package com.k2.custom;

import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.k2.custom.cview.FristView;
import com.k2.custom.cview.RandomMath;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Integer> strList;
    private FristView frist_view;
    private final int MAX = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关联后修改
        /**
         * 分支代码
         */
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);
        frist_view = (FristView) findViewById(R.id.frist_view);
        frist_view.setMax(MAX);
        strList = new ArrayList<>();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data();
            }
        });
        data();
        Log.i("xxx","手机型号:" + isAppOnForeground());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("xxx","onRestart:" + isAppOnForeground());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("xxx","onResume:" + isAppOnForeground());
    }

    // 数据
    private void data() {
        strList.clear();
        for (int i = 0; i < 7; i++) {
            strList.add(RandomMath.math(1, MAX));
        }
        frist_view.setData(strList);
    }
    /**
     * 程序是否在前台运行
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the device
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
