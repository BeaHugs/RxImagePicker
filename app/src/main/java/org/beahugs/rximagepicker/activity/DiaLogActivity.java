package org.beahugs.rximagepicker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.beahugs.rximagepicker.R;

public class DiaLogActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_log);
        findViewById(R.id.open_dialog_loading).setOnClickListener(this);
        findViewById(R.id.open_dialog_alter).setOnClickListener(this);
        findViewById(R.id.open_dialog_bottom).setOnClickListener(this);
        findViewById(R.id.open_dialog_avder).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_dialog_loading:

                break;
            case R.id.open_dialog_alter:

                break;
            case R.id.open_dialog_bottom:

                break;
            case R.id.open_dialog_avder:

                break;
        }
    }
}
