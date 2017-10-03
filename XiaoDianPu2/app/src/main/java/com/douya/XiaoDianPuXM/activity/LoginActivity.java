package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.os.Bundle;

import com.douya.XiaoDianPuXM.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
		//开启一个线程，进行休眠，然后页面跳转
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    //初始化Intent对象
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    //跳转页面
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
