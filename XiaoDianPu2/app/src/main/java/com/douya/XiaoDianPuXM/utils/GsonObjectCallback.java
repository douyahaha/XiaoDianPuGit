package com.douya.XiaoDianPuXM.utils;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 类的用途 如果要将得到的json直接转化为集合 建议使用该类的
 * onUi()
 * onFailed()方法运行在主线程
 * Created by 杨圆圆 on 2017/10/10.
 */

public abstract class GsonObjectCallback<T> implements Callback {
    //得到Handler对象
    private Handler handler=OKHttp3Utils.getHandler();
    //主线程处理
    public abstract void onUi(T t);
    //主线程处理
    public abstract void onFailed(Call call,IOException e);
    //请求失败
    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(call, e);
            }
        });
    }
    //请求成功，JSON 并直接返回泛型的对象 主线程处理
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //将请求结果转换成字符串
        String json = response.body().string();

        Class<T> cls=null;
        Class clz=this.getClass();
        //表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
        //简而言之就是获得超类的泛型参数的实际类型
        ParameterizedType type= (ParameterizedType) clz.getGenericSuperclass();
        // 获取泛型类型的泛型参数
        Type[] types = type.getActualTypeArguments();
        cls= (Class<T>) types[0];
        //获取Gson对象
        Gson gson=new Gson();
        //将内容进行解析
        final T t = gson.fromJson(json, cls);
        //通过Handler发送消息
        handler.post(new Runnable() {
            @Override
            public void run() {
                //将对象交给主线程
                onUi(t);
            }
        });
    }
}
