package com.douya.XiaoDianPuXM.port;

/**
 * Created by 杨圆圆 on 2017/10/11.
 */

public interface RegFinishListener {
    void NameEmpty();
    void PwdEmpty();
    void AgainPwdEmpty();
    void EmailEmpty();
    void RegSucceed();
    void RegFailed();
}
