package com.douya.XiaoDianPuXM.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.RegBean;
import com.douya.XiaoDianPuXM.port.RegFinishListener;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/11.
 */

public class RegModelImpl implements RegModelInterface {


    private RegFinishListener listener;

    public RegModelImpl(RegFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void RegLogin(Context context, String regName, String regPwd, String regAgainPwd, String regEmail) {
        //注册用户名为空时
        if(TextUtils.isEmpty(regName)){
            //调用方法
            listener.NameEmpty();
            return;
        }
        //注册密码为空时
        if(TextUtils.isEmpty(regPwd)){
            //调用方法
            listener.PwdEmpty();
            return;
        }
        //注册确认密码为空时
        if(TextUtils.isEmpty(regAgainPwd)){
            //调用方法
            listener.AgainPwdEmpty();
            return;
        }
        //注册邮箱为空时
        if(TextUtils.isEmpty(regEmail)){
            //调用方法
            listener.EmailEmpty();
            return;
        }
        regRequest(context, regName, regPwd, regAgainPwd, regEmail);


    }

    @Override
    public void regRequest(final Context context, String regName, String regPwd, String regAgainPwd, String regEmail) {
        //定义一个Map集合将参数进行封装
        Map<String ,String> regParams=new HashMap<String,String>();
        //将参数添加到集合
        regParams.put("username",regName);
        regParams.put("password",regPwd);
        regParams.put("password_confirm",regAgainPwd);
        regParams.put("email",regEmail);
        regParams.put("client", API.CLIENT);
        OKHttp3Utils.doPost(API.REG_PATH, regParams, new GsonObjectCallback<RegBean>() {
            @Override
            public void onUi(RegBean regBean) {
                Log.i("内容",regBean.toString());
                if(regBean.getCode()==200){
                   listener.RegSucceed();
                }else if(regBean.getCode()==400){
                    listener.RegFailed();
                }
            }
            @Override
            public void onFailed(Call call, IOException e) {
            }
        });
    }

}
