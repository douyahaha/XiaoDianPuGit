package com.douya.XiaoDianPuXM.model;

import android.content.Context;

/**
 * Created by 杨圆圆 on 2017/10/13.
 */

public interface LoginModelInterface {
    void Login(Context context,String loginName,String loginPwd);
    void LoginRequest(String loginName,String loginPwd);
}
