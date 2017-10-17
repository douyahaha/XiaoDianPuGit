package com.douya.XiaoDianPuXM.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.fragment.Fragment_classify;
import com.douya.XiaoDianPuXM.fragment.Fragment_homepage;
import com.douya.XiaoDianPuXM.fragment.Fragment_my;
import com.douya.XiaoDianPuXM.fragment.Fragment_shoppingcar;

public class MainActivity extends BaseActivity {

    private RadioButton bt_homepage;

    private RadioGroup group;
    private FragmentManager manager;
    private Fragment_homepage homepage;
    private FragmentTransaction transaction;
    private Fragment_classify classify;
    private Fragment_shoppingcar shoppingcar;
    private Fragment_my my;

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
            homepage = new Fragment_homepage();
            transaction.add(R.id.frameLayout,homepage);
            transaction.commit();
        }

        //给RadioGroup设置选中监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideFragment();
                switch (checkedId){
                    case R.id.bt_homepage:
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        if(homepage==null){
                            homepage=new Fragment_homepage();
                            transaction1.add(R.id.frameLayout,homepage);
                        }else{
                            transaction1.show(homepage);
                        }
                        transaction1.commit();
                        break;
                    case R.id.bt_classfig:
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        if(classify==null){
                            classify = new Fragment_classify();
                            transaction2.add(R.id.frameLayout,classify);
                        }else{
                            transaction2.show(classify);
                        }
                        transaction2.commit();
                        break;
                    case R.id.bt_shoppingcar:
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        if(shoppingcar==null){
                            shoppingcar = new Fragment_shoppingcar();
                            transaction3.add(R.id.frameLayout,shoppingcar);
                        }else{
                            transaction3.show(shoppingcar);
                        }
                        transaction3.commit();
                        break;
                    case R.id.bt_my:
                        FragmentTransaction transaction4 = manager.beginTransaction();
                        if(my==null){
                            my = new Fragment_my();
                            transaction4.add(R.id.frameLayout,my);
                        }else{
                            transaction4.show(my);
                        }
                        transaction4.commit();
                        break;
                }
            }
        });

    }

    private void initView() {
        bt_homepage = (RadioButton) findViewById(R.id.bt_homepage);
        group = (RadioGroup) findViewById(R.id.group);
    }
    private void hideFragment(){
        FragmentTransaction transaction = manager.beginTransaction();
        if(homepage!=null){
            transaction.hide(homepage);
        }
        if(classify!=null){
            transaction.hide(classify);
        }
        if(shoppingcar!=null){
            transaction.hide(shoppingcar);
        }
        if(my!=null){
            transaction.hide(my);
        }
        transaction.commit();
    }

}
