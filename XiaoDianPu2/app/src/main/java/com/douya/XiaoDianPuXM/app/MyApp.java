package com.douya.XiaoDianPuXM.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/9.
 */

public class MyApp extends Application {
    public static MyApp mapp;
    public static List<Activity> list=new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        mapp=this;

    }
    public static MyApp getInstance(){
        return mapp;
    }
    //添加Activity
    public static void addActivity(Activity activity){
        list.add(activity);
    }
    //删除Activity
    public static void removeActivity(Activity activity){
        list.remove(activity);
    }
    //删除所有Activity
    public static void removeAllActivity(){
        for(Activity acitivity:list){
            acitivity.finish();
        }
    }
}
