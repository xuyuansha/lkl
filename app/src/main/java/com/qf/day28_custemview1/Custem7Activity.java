package com.qf.day28_custemview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qf.widget7.SwitchButton;

/**
 *
 */
public class Custem7Activity extends AppCompatActivity{

    private static final String TAG = "print";
    private SwitchButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchbutton);

        switchButton = (SwitchButton) findViewById(R.id.sb_id);
        switchButton.setOnSwitchCheckedListener(new SwitchButton.OnSwitchCheckedListener() {
            @Override
            public void onCheckedChange(SwitchButton switchBtn, boolean isChecked) {
                Log.d(TAG, "onCheckedChange: 当前开关的状态" + isChecked);
            }
        });
        switchButton.setChecked(true);
    }
}
