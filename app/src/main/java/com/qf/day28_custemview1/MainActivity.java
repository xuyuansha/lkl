package com.qf.day28_custemview1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qf.widget.BallView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View v){
        switch (v.getId()){
            case R.id.btn_01:
                startActivity(new Intent(this, Custem1Activity.class));
                break;
            case R.id.btn_02:
                startActivity(new Intent(this, Custem2Activity.class));
                break;
            case R.id.btn_03:
                startActivity(new Intent(this, Custem3Activity.class));
                break;
            case R.id.btn_04:
                startActivity(new Intent(this, Custem4Activity.class));
                break;
            case R.id.btn_05:
                startActivity(new Intent(this, Custem5Activity.class));
                break;
            case R.id.btn_06:
                startActivity(new Intent(this, Custem6Activity.class));
                break;
            case R.id.btn_07:
                startActivity(new Intent(this, Custem7Activity.class));
                break;
            case R.id.btn_08:
                startActivity(new Intent(this, Custem8Activity.class));
                break;
            case R.id.btn_09:
                startActivity(new Intent(this, Custem9Activity.class));
                break;
        }
    }
}
