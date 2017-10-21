package com.douya.XiaoDianPuXM.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 杨圆圆 on 2017/10/7.
 */

public class API {
//    public static final String url="http://169.254.165.38/";
public static final String url="http://169.254.110.146/";
    public static final String CLIENT="android";
    //注册的接口
    public static final String REG_PATH=url+"mobile/index.php?act=login&op=register";
    //登录的接口
    public static final String LOGIN_PATH=url+"mobile/index.php?act=login";
    //注销的接口
    public static final String CANCEL_PATH=url+"mobile/index.php?act=logout";
    //一级列表的接口
    public static final String TYPE_PATH=url+"mobile/index.php?act=goods_class";
    //查找商品接口
    public static final String SELECT_PATH=url+"mobile/index.php?act=goods&op=goods_list&page=100";
    //查找商品详细信息
    public static final String SELECT_GOODS_PATH=url+"mobile/index.php?act=goods&op=goods_detail";
    //添加购物车
    public static final String POST_SHOPPING_CART=url+"mobile/index.php?act=member_cart&op=cart_add";
    //获取购物车信息
    public static final String GET_SHOPPING_CART=url+"mobile/index.php?act=member_cart&op=cart_list";
    //webView加载的具体信息接口
    public static final String GOODS_PATH=url+"mobile/index.php?act=goods&op=goods_body";
    //删除字条目的监听
    public static final String REMOVE_SHOPPING_CART=url+"mobile/index.php?act=member_cart&op=cart_del";
    //添加收货地址
    public static final String POST_ADDRESS=url+"mobile/index.php?act=member_address&op=address_add";
    //获取收货地址列表
    public static final String GET_ADDRESS=url+"mobile/index.php?act=member_address&op=address_list";
    /**
     * 定义一个全局的SharedPreferences
     */
    //系统变量
    public static SharedPreferences sp;
    public static  SharedPreferences.Editor mEditor ;

    //初始化
    public static void init(Context context){
        sp=context.getSharedPreferences("User",Context.MODE_PRIVATE);
        mEditor = sp.edit();


    }

}
