package com.douya.XiaoDianPuXM.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.douya.XiaoDianPuXM.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //将标题栏进行隐藏
        getSupportActionBar().hide();
    }
}
