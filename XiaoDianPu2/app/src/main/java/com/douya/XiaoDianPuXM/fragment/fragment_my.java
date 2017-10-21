package com.douya.XiaoDianPuXM.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.activity.AddressActivity;
import com.douya.XiaoDianPuXM.activity.LoginActivity;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.CancelBean;
import com.douya.XiaoDianPuXM.bean.RegBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/3.
 */

public class Fragment_my extends Fragment {
    private ImageView iv_header;
    private TextView tv_login;
    private LinearLayout linear_address;
    private View popView;
    private PopupWindow mWindow;
    private Button bt_cancel;
    private String login_cancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);

        //点击头像
        iv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupWindow展示
                mWindow.showAsDropDown(tv_login, 0, 20);
                //判断当前是否登录
                if(API.sp.getBoolean("isLogin", false)){
                    //当前为登录状态，将当前
                    tv_login.setText(API.sp.getString("userName",""));
                    bt_cancel.setText("注销");
                }else{
                    tv_login.setText("未登录");
                    bt_cancel.setText("登录");
                }
            }
        });
        //我的地址的点击监听
        linear_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前是否登录
                if(API.sp.getBoolean("isLogin", false)){
                    //跳转到地址页面
                    startActivity(new Intent(getContext(), AddressActivity.class));
                }else {
                    //进行页面跳转到登录页面
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        return view;
    }


    private void initView(View view) {
        iv_header = (ImageView) view.findViewById(R.id.iv_header);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        linear_address = (LinearLayout) view.findViewById(R.id.linear_address);
        popView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_view, null);
        //创建PopupWindow
        mWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, 100);
        //设置点击PopupWindow外部，关闭PopupWindow
        mWindow.setBackgroundDrawable(new ColorDrawable());
        mWindow.setOutsideTouchable(true);
        //查找PopupWindow中的控件
        bt_cancel = (Button) popView.findViewById(R.id.bt_cancel);
        //给PopupWindow中的控件设置点击监听
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取PopupWindow中的按钮当前的内容
                login_cancel = bt_cancel.getText().toString();
                //根据内容执行不同的操作
                if (login_cancel.equals("登录")) {//当前显示为登录时
                    //进行页面跳转到登录页面
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    //PopupWindow关闭
                    mWindow.dismiss();
                } else if(login_cancel.equals("注销")){//当前显示为注销时
                    //定义一个Map集合用来存
                    Map<String, String> cancelParams = new HashMap<>();
                    cancelParams.put("key", API.sp.getString("userKey", ""));
                    cancelParams.put("username", API.sp.getString("userName", ""));
                    cancelParams.put("client", API.CLIENT);
                    //进行注销请求
                    OKHttp3Utils.doPost(API.CANCEL_PATH, cancelParams, new GsonObjectCallback<CancelBean>() {
                        @Override
                        public void onUi(CancelBean cancelBean) {
                            //请求码返归为200时，注销成功
                            if (cancelBean.getCode() == 200) {
                                Toast.makeText(getContext(), "注销成功", Toast.LENGTH_SHORT).show();
                                //将登录成功时保存的用户信息置空
                                API.mEditor.putBoolean("isLogin", false);
                                API.mEditor.putString("userID","");
                                API.mEditor.putString("userName","");
                                API.mEditor.putString("userKey","");
                                API.mEditor.commit();
                                //
                                tv_login.setText("未登录");
                                bt_cancel.setText("登录");
                                mWindow.dismiss();
                            }
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断当前是否登录
        if(API.sp.getBoolean("isLogin", false)){
            //当前为登录状态，将当前用户名设置给
            tv_login.setText(API.sp.getString("userName",""));
            bt_cancel.setText("注销");
        }else{
            tv_login.setText("未登录");
            bt_cancel.setText("登录");
        }
    }

    //接收EventBus发送来的消息，通过注解的方式指定接收消息线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(RegBean regBean) {
        //将接收的消息设置给TextView
        tv_login.setText(regBean.getDatas().getUsername());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
