package com.douya.XiaoDianPuXM.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by 杨圆圆 on 2017/10/7.
 */

public class NetWorkUtils {

    public static boolean isNetWorkAvailable(Context context){
        //网络连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            return true;
        }

        return false;
    }

}
