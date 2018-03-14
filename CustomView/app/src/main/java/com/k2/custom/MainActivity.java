package com.k2.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    // 数据
    private void data() {
        strList.clear();
        for (int i = 0; i < 7; i++) {
            strList.add(RandomMath.math(1, MAX));
        }
        frist_view.setData(strList);
    }
}
