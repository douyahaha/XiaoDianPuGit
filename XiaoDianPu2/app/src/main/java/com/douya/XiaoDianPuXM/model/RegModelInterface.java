package com.douya.XiaoDianPuXM.model;

import android.content.Context;

/**
 * Created by 杨圆圆 on 2017/10/11.
 */

public interface RegModelInterface {
    void RegLogin(Context context,String regName, String regPwd, String regAgainPwd, String regEmail);
    void regRequest(Context context,String regName, String regPwd, String regAgainPwd, String regEmail);
}
