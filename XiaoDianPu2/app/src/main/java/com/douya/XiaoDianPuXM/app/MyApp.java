package com.douya.XiaoDianPuXM.app;

import android.app.Application;

/**
 * Created by 杨圆圆 on 2017/10/9.
 */

public class MyApp extends Application {
    public static MyApp mapp;

    @Override
    public void onCreate() {
        super.onCreate();
        mapp=this;
    }
    public static MyApp getInstance(){
        return mapp;
    }
}
