package com.douya.XiaoDianPuXM.model;

import android.content.Context;
import android.text.TextUtils;

import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.RegBean;
import com.douya.XiaoDianPuXM.port.LoginFinishListener;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/13.
 */

public class LoginModelImpl implements LoginModelInterface {
    private LoginFinishListener listener;

    public LoginModelImpl(LoginFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void Login(Context context, String loginName, String loginPwd) {
        if(TextUtils.isEmpty(loginName)){
            listener.LoginNameEmpty();
            return;
        }
        if (TextUtils.isEmpty(loginPwd)){
            listener.LoginPwdEmpty();
            return;
        }
        //当用户名和密码都不为空时进行登录请求
        LoginRequest(loginName,loginPwd);
    }

    @Override
    public void LoginRequest(String loginName, String loginPwd) {
        //定义Map集合用来存放参数
        Map<String,String> loginParams=new HashMap<>();
        //将参数添加到集合
        loginParams.put("username",loginName);
        loginParams.put("password",loginPwd);
        loginParams.put("client", API.CLIENT);
        //进行登录请求
        OKHttp3Utils.doPost(API.LOGIN_PATH, loginParams, new GsonObjectCallback<RegBean>() {
            @Override
            public void onUi(RegBean regBean) {
                //当请求码为200时，请求成功
                if(regBean.getCode()==200){
                    RegBean.DatasBean datas = regBean.getDatas();
                    API.mEditor.putBoolean("isLogin",true);
                    API.mEditor.putString("userID",datas.getUserid());
                    API.mEditor.putString("userName",datas.getUsername());
                    API.mEditor.putString("userKey",datas.getKey());
                    API.mEditor.commit();
                    EventBus.getDefault().post((regBean));
                    listener.LoginSucceed();
                }
                //当请求码为400时，请求失败
                if(regBean.getCode()==400){
                    listener.LoginFailed();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}
