package com.qf.day28_custemview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qf.widget5.LabelView;
import com.qf.widget5.SibeView;

/**
 * Created by Ken on 2016/1/28.
 */
public class Custem5Activity extends AppCompatActivity{

    private static final String TAG = "print";
    private SibeView sibeView;
    private LabelView labelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sibeview);

        sibeView = (SibeView) findViewById(R.id.sv_id);
        labelView = (LabelView) findViewById(R.id.lv_id);

        sibeView.setOnSideSelectedListener(new SibeView.OnSideSelectedListener() {
            @Override
            public void onSelected(int index, String strs) {
                Log.d(TAG, "onSelected: 接口回调：" + index + "   " + strs);
                labelView.setText(strs);

                //实现和ListView之间的级联
            }
        });
    }
}
