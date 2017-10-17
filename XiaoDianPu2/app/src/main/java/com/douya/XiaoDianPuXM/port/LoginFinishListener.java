package com.douya.XiaoDianPuXM.port;

/**
 * Created by 杨圆圆 on 2017/10/13.
 */

public interface LoginFinishListener {
    void LoginNameEmpty();
    void LoginPwdEmpty();
    void LoginSucceed();
    void LoginFailed();
}
