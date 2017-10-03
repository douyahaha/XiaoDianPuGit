package com.douya.XiaoDianPuXM.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.fragment.fragment_homepage;

public class MainActivity extends BaseActivity {

    private FrameLayout frameLayout;
    private RadioButton bt_homepage;
    private RadioButton bt_classfig;
    private RadioButton bt_shoppingcar;
    private RadioButton bt_my;
    private RadioGroup group;
    private FragmentManager manager;
    private fragment_homepage homepage;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //查找组件
        initView();
        //获取Fragment管理者
        manager = getSupportFragmentManager();
        //开启一个事物
        transaction = manager.beginTransaction();
        //判断首页是否选中
        if(bt_homepage.isChecked()){
            homepage = new fragment_homepage();
            transaction.add(R.id.frameLayout,homepage);
            transaction.commit();
        }

        //给RadioGroup设置选中监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.bt_homepage:
                        break;
                    case R.id.bt_classfig:
                        break;
                    case R.id.bt_shoppingcar:
                        break;
                    case R.id.bt_my:
                        break;
                }
            }
        });

    }

    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        bt_homepage = (RadioButton) findViewById(R.id.bt_homepage);
        bt_classfig = (RadioButton) findViewById(R.id.bt_classfig);
        bt_shoppingcar = (RadioButton) findViewById(R.id.bt_shoppingcar);
        bt_my = (RadioButton) findViewById(R.id.bt_my);
        group = (RadioGroup) findViewById(R.id.group);
    }
}
