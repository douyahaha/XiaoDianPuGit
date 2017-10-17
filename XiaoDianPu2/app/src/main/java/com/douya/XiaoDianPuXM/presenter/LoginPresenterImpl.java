package com.douya.XiaoDianPuXM.presenter;

import android.content.Context;

import com.douya.XiaoDianPuXM.model.LoginModelImpl;
import com.douya.XiaoDianPuXM.port.LoginFinishListener;
import com.douya.XiaoDianPuXM.view.LoginViewInterface;

/**
 * Created by 杨圆圆 on 2017/10/13.
 */

public class LoginPresenterImpl implements LoginPresenterInterface,LoginFinishListener {
    private LoginViewInterface loginViewInterface;
    private final LoginModelImpl loginModel;

    public LoginPresenterImpl(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
        //利用多态的方法初始化loginModel
        loginModel = new LoginModelImpl(this);
    }

    @Override
    public void loginGetData(Context context, String loginName, String loginPwd) {
        //如果loginModel不等于空，调用loginModel的方法
        if(loginModel!=null){
            loginModel.Login(context, loginName, loginPwd);
        }
    }

    @Override
    public void LoginNameEmpty() {
        if(loginViewInterface!=null){
            loginViewInterface.onLoginNameEmpty();
        }
    }

    @Override
    public void LoginPwdEmpty() {
        if(loginViewInterface!=null){
            loginViewInterface.onLoginPwdEmpty();
        }
    }

    @Override
    public void LoginSucceed() {
        if(loginViewInterface!=null){
            loginViewInterface.onLoginSucceed();
        }
    }

    @Override
    public void LoginFailed() {
        if(loginViewInterface!=null){
            loginViewInterface.onLoginFailed();
        }
    }
}
