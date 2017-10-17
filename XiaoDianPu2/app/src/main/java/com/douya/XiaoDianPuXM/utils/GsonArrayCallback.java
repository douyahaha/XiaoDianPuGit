package com.douya.XiaoDianPuXM.utils;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 杨圆圆 on 2017/10/10.
 */

public abstract class GsonArrayCallback<T> implements Callback {
    //获取Handler对象
    private Handler handler=OKHttp3Utils.getHandler();
    //主线程处理
    public abstract void onUi(List<T> list);
    //主线程处理
    public abstract void onFiled(Call call,IOException e);
    //请求失败
    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(call, e);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //创建一个集合
        final List<T> list=new ArrayList<T>();
        //将获取的数据转换成字符串
        String json = response.body().string();
        //Json的解析类对象 JsonParser()将JSON的String 转成一个JsonArray对象
        JsonArray array=new JsonParser().parse(json).getAsJsonArray();
        //获取Gson对象
        Gson gson=new Gson();
        //
        Class<T> cls=null;
        Class clz=this.getClass();
        ParameterizedType type= (ParameterizedType) clz.getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        cls= (Class<T>) types[0];

        /**
         * JsonElement 四种具体实现类型：
         * JsonPrimitive (Java Doc) —— 例如一个字符串或整型
         * JsonObject (Java Doc) —— 一个以 JsonElement 名字（类型为 String）作为索引的集合。类似于 Map<String,JsonElement>集合（Java Doc）
         * JsonArray (Java Doc)—— JsonElement 的集合。注意数组的元素可以是四种类型中的任意一种，或者混合类型都支持。
         * JsonNull (Java Doc) —— 值为null
         */
        //遍历集合
        for(JsonElement elem:array){
            list.add((gson.fromJson(elem,cls)));
        }
        //通过Handler将集合发送到主线程
        handler.post(new Runnable() {
            @Override
            public void run() {
                onUi(list);
            }
        });
    }
}
