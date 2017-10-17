package com.douya.XiaoDianPuXM.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.activity.LoginActivity;
import com.douya.XiaoDianPuXM.api.API;

/**
 * Created by 杨圆圆 on 2017/10/3.
 */

public class Fragment_shoppingcar extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!API.sp.getBoolean("isLogin",false)){
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView textView=new TextView(getContext());
            textView.setText("我是购物车");
            return textView;

    }

}
