package org.beahugs.rximagepicker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import org.beahugs.rximagepicker.R;
import org.beahugs.rxpopup.dialog.IDialog;
import org.beahugs.rxpopup.RxDialog;
import org.beahugs.rxpopup.utils.type.DiaLogSettings;

public class DiaLogActivity extends AppCompatActivity implements View.OnClickListener {

    DiaLogSettings.STYLE style;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_log);
        findViewById(R.id.open_dialog_loading).setOnClickListener(this);
        findViewById(R.id.open_dialog_alter).setOnClickListener(this);
        findViewById(R.id.open_dialog_bottom).setOnClickListener(this);
        findViewById(R.id.open_dialog_avder).setOnClickListener(this);
        style = DiaLogSettings.STYLE.STYLE_IOS;
        RadioButton style_one = findViewById(R.id.style_one);

        RadioButton style_two = findViewById(R.id.style_two);

        style_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = DiaLogSettings.STYLE.STYLE_IOS;
            }
        });
        style_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = DiaLogSettings.STYLE.STYLE_USE;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_dialog_loading:
                new RxDialog.Builder(this)
                        .setTitle("title")
                        .setContent("您好啊")
                        .setStyle(style)
                        .setDialogView(R.layout.load_layout)
                        .setAnimStyle(R.style.iOSDialogAnimStyle)
                        .setNegativeButton(new IDialog.OnClickListener() {
                            @Override
                            public void onClick(IDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(new IDialog.OnClickListener() {
                            @Override
                            public void onClick(IDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
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
