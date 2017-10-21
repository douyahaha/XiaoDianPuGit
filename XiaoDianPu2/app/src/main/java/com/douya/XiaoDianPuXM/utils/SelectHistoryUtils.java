package com.douya.XiaoDianPuXM.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杨圆圆 on 2017/10/17.
 */

public class SelectHistoryUtils {
    private static SharedPreferences sharedPreferences;

    /**
     * 得到SharedPreferences所有的数据
     * @param context
     * @return
     */
    public static Map<String,String> getAll(Context context){
        Map<String,String> map=new HashMap<>();
        sharedPreferences=context.getSharedPreferences("history",Context.MODE_PRIVATE);
        map = (Map<String, String>) sharedPreferences.getAll();
        return map;
    }
    public static String getFirst(Context context){
        sharedPreferences=context.getSharedPreferences("history",Context.MODE_PRIVATE);
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String s = sharedPreferences.getString(format.format(System.currentTimeMillis()), "嘿嘿嘿");
        return s;
    }
    //将信息删除
    public static void remove(Context context,String key){
        sharedPreferences=context.getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.commit();
    }
    //添加值
    public static void put(Context context,String key,String value){
        sharedPreferences=context.getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();
    }
    //清除
    public static void clear(Context context){
        sharedPreferences=context.getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }


}
