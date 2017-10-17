package com.douya.XiaoDianPuXM.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 杨圆圆 on 2017/10/7.
 */

public class API {
    public static final String url="http://169.254.165.38/";
    public static final String CLIENT="android";
    //注册的接口
    public static final String REG_PATH=url+"mobile/index.php?act=login&op=register";
    //登录的接口
    public static final String LOGIN_PATH=url+"mobile/index.php?act=login";
    //注销的接口
    public static final String CANCEL_PATH=url+"mobile/index.php?act=logout";
    //一级列表的接口
    public static final String TYPE_PATH=url+"mobile/index.php?act=goods_class";
    /**
     * 定义一个全局的SharedPreferences
     */
    //系统变量
    public static SharedPreferences sp;
    public static  SharedPreferences.Editor mEditor ;
    //判断登录状态
    public static boolean isLogin;
    //用户ID
    public static String userID;
    //用户名称
    public static String userName;
    //用户的Key值
    public static String userKey;
    //初始化
    public static void init(Context context){
        sp=context.getSharedPreferences("User",Context.MODE_PRIVATE);
        mEditor = sp.edit();

        isLogin=sp.getBoolean("isLogin",false);
        userID=sp.getString("userID","");
        userName=sp.getString("userName","");
        userKey=sp.getString("userKey","");
    }

}
