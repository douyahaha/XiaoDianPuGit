package com.douya.XiaoDianPuXM.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.douya.XiaoDianPuXM.app.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKHttp3Utils {
    /**
     * 懒汉 安全 加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    //定义私有的静态成员变量
    private static OKHttp3Utils okHttp3Utils=null;
    //私有的构造方法
    private OKHttp3Utils(){

    }
    //公共的静态方法用来返回实例
    public static OKHttp3Utils getInstance(){
        if(okHttp3Utils==null){
            //加同步安全
            synchronized (OKHttp3Utils.class){
                if(okHttp3Utils==null){
                    okHttp3Utils=new OKHttp3Utils();
                }
            }
        }
        return okHttp3Utils;
    }
    /**
     * 封装OKHttpClient对象
     *
     */
    //定义私有的静态的OKHttpClient成员变量
    private static OkHttpClient okHttpClient=null;
    //定义公共的返回OkHttpClient实例的方法
    public synchronized static OkHttpClient getOkHttpClient(){
        if(okHttpClient==null){
            //缓存目录
            File sdcache=new File(Environment.getExternalStorageDirectory(),"cache");
            //设置大小
            int cacheSize=10*1024*1024;
            //OKHttp3拦截器
            HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("XXX",message.toString());
                }
            });
            //OKHttp3的拦截器日志分类 4种
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //初始化OKHTTPClient对象
            //判空 为空创建实例
            // okHttpClient = new OkHttpClient();
        /**
         * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，
         * 而是通过OkHttpClient.Builder来设置，
         * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
         * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，
         * 而是通过builder.build()：
         */
            okHttpClient =new OkHttpClient.Builder()
                    //添加日志拦截
                    .addInterceptor(httpLoggingInterceptor)
                    //添加网络拦截，进行缓存或从内存中读取
                    .addNetworkInterceptor(new CacheInterceptor())
                    //添加网络连接超时时间
                    .connectTimeout(20,TimeUnit.SECONDS)
                    //设置写超时时间
                    .writeTimeout(20, TimeUnit.SECONDS)
                    //设置读取超时时间
                    .readTimeout(20,TimeUnit.SECONDS)
                    //设置缓存路径
                    .cache(new Cache(sdcache.getAbsoluteFile(),cacheSize))
                    .build();
        }
        return okHttpClient;
    }
    /**
     *封装Handler，返回的此实例在二次封装的CallBack中使用
     */
    private static Handler mHandler=null;
    public synchronized static Handler getHandler(){
        if(mHandler==null){
            mHandler=new Handler();
        }
        return mHandler;
    }
    /**
     * get请求
     * 参数1：url
     * 参数2：回调CallBack
     */
    public static void doGet(String url, Callback callback){
        //创建OKHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建Request对象
        Request request=new Request.Builder()
                .url(url)
                .build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(callback);
    }
    /**
     * post请求
     * 参数1 url
     * 参数2 参数集合
     * 参数3 回调CallBack
     */
    public static void doPost(String url, Map<String,String> params,Callback callback){
        //创建OKHttpClient对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //3.X版本post请求换成FormBody封装键值对参数
        FormBody.Builder builder=new FormBody.Builder();
        //遍历参数集合
        for(String key:params.keySet()){
            //根据key值得到value值
            String value = params.get(key);
            //将值添加到FormBody.Builder
            builder.add(key,value);
        }
        //创建Body
        FormBody body = builder.build();
        //创建Request对象
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //进行异步请求
        call.enqueue(callback);
    }
    /**
     * post请求上传文件
     */
    public static void uploadPic(String url,File file,String fileName){
        //创建OKHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建RequestBody封装file对象
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody设置类型等
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",fileName,fileBody)
                .build();
        //创建Request对象
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    /**
     * post请求发送JSON数据
     * 参数1 请求Url
     * 参数2 请求的JSON
     * 参数3 请求回调
     */
    public static void doPostJson(String url,String jsonParams,Callback callback){
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonParams);
        //创建Request对象
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //创建Call对象
        Call call = getOkHttpClient().newCall(request);
        //进行异步请求
        call.enqueue(callback);
    }
    /**
     * 下载文件 以流的形式把apk写入指定文件 得到file文件后进行安装
     * 参数1 请求Url
     * 参数2 保存文件的路径名
     * 参数3 保存文件的文件名
     */
    public static void download(final Context context, final String url, final String saveDir){
        //创建Request对象
        Request request=new Request.Builder()
                .url(url)
                .build();
        //获取Call对象
        Call call = getOkHttpClient().newCall(request);
        //进行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("xxx",e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is=null;
                byte[] buffer=new byte[1024];
                int len=0;
                FileOutputStream fos=null;
                try {
                    is=response.body().byteStream();

                    String fileDir = isExistDir(saveDir);

                    File file=new File(fileDir,getNameFromUrl(url));

                    fos=new FileOutputStream(file);
                    //执行读写操作
                    while((len=is.read(buffer))!=-1){
                        fos.write(buffer,0,len);
                    }
                    //刷新流
                    fos.flush();
                    //apk下载完成后 调用系统的安装方法
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                    context.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭流
                    if(is!=null){
                        is.close();
                    }
                    if(fos!=null){
                        fos.close();
                    }
                }
            }
        });
    }
    /**
     * 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {

        if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){

            File downloadFile=new File(Environment.getExternalStorageDirectory(),saveDir);
            if(!downloadFile.mkdirs()){
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            Log.e("savePath",savePath);
            return savePath;
        }
        return null;
    }
    /**
     * 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }

    /**
     * 为OKHttp添加缓存，考虑到服务器不支持缓存时，让OKHttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor{

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Response intercept(Chain chain) throws IOException {
            //有网络时设置缓存超时时间1个小时
            int maxAge=60*60;
            //无网络时，设置超时时为1天
            int maxStale=60*60*24;

            Request request = chain.request();
            //判断当前是否有网
            if(NetWorkUtils.isNetWorkAvailable(MyApp.getInstance())){
                //有网络时只从缓存中读取
                request=request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            }else{
                //无网络时只从缓存中读取
                request=request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if(NetWorkUtils.isNetWorkAvailable(MyApp.getInstance())){
                response=response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public,max-age="+maxAge)
                        .build();
            }else {
                response=response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public,only-if-cached,max-stale="+maxStale)
                        .build();
            }
            return response;
        }
    }
}
