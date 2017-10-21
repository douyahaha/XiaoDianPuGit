package com.douya.XiaoDianPuXM.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.activity.IndentActivity;
import com.douya.XiaoDianPuXM.activity.LoginActivity;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.CancelBean;
import com.douya.XiaoDianPuXM.bean.ShoppingCartGoodsBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/3.
 */

public class Fragment_shoppingcar extends Fragment implements View.OnClickListener {

    private ExpandableListView elv;
    private CheckBox cb_total;
    private TextView tv_total;
    private Button bt_total;
    private LinearLayout linear_total;
    private TextView tv_tiShi;
    private List<ShoppingCartGoodsBean.DatasBean.CartListBean> cart_list;
    private List<ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean> goodsBeanList;
    private ShoppingCartGoodsBean scgb=null;
    private MyExpandableAdapter expandableAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!API.sp.getBoolean("isLogin", false)) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        initView(view);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (API.sp.getBoolean("isLogin", false)) {
            //获取数据
            getShoppingCartData();
        } else {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }
    //获取购物车的数据
    private void getShoppingCartData() {
        //购物车数据参数
        Map<String, String> cartParams = new HashMap<>();
        cartParams.put("key", API.sp.getString("userKey", ""));
        //请求数据
        OKHttp3Utils.doPost(API.GET_SHOPPING_CART, cartParams, new GsonObjectCallback<ShoppingCartGoodsBean>() {
            @Override
            public void onUi(ShoppingCartGoodsBean shoppingCartGoodsBean) {
                scgb=shoppingCartGoodsBean;
                //得到数据集合
                cart_list = shoppingCartGoodsBean.getDatas().getCart_list();
                //判断是否有值
                if (cart_list.size()==0) {
                    //没有值将列表和总价隐藏，显示提示语句
                    elv.setVisibility(View.GONE);
                    linear_total.setVisibility(View.GONE);
                    tv_tiShi.setVisibility(View.VISIBLE);
                    return;
                }
                elv.setVisibility(View.VISIBLE);
                linear_total.setVisibility(View.VISIBLE);
                tv_tiShi.setVisibility(View.GONE);
                //有值的时候初始化适配器
                expandableAdapter = new MyExpandableAdapter(getContext(), cart_list);
                //给二级列表设置适配器
                elv.setAdapter(expandableAdapter);
                //获取二级列表的数目，将所有字条目展开
                int count = elv.getCount();
                for (int i=0;i<count;i++){
                    elv.expandGroup(i);
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    private void initView(View view) {
        elv = (ExpandableListView) view.findViewById(R.id.elv);
        cb_total = (CheckBox) view.findViewById(R.id.cb_total);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        bt_total = (Button) view.findViewById(R.id.bt_total);
        linear_total = (LinearLayout) view.findViewById(R.id.linear_total);
        tv_tiShi = (TextView) view.findViewById(R.id.tv_tiShi);

        bt_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到订单页面
                startActivity(new Intent(getContext(), IndentActivity.class));
            }
        });
        //设置二级列表箭头为空
        elv.setGroupIndicator(null);
        cb_total.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(((CheckBox)v).isChecked()){
            //获取店铺条目
            List<ShoppingCartGoodsBean.DatasBean.CartListBean> cartList = scgb.getDatas().getCart_list();
            for (int i=0;i<cartList.size();i++){
                //将店铺设为选中
                cartList.get(i).setAllCheck(true);
                //获取字条目对象
                List<ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean> goods = cartList.get(i).getGoods();
                for (int j=0;j<goods.size();j++){
                    goods.get(j).setItemCheck(true);
                }
            }
            notifyCheckAdapter();
        }else{
            //获取店铺条目
            List<ShoppingCartGoodsBean.DatasBean.CartListBean> cartList = scgb.getDatas().getCart_list();
            for (int i=0;i<cartList.size();i++){
                //将店铺设为选中
                cartList.get(i).setAllCheck(false);
                //获取字条目对象
                List<ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean> goods = cartList.get(i).getGoods();
                for (int j=0;j<goods.size();j++){
                    goods.get(j).setItemCheck(false);
                }
            }
            notifyCheckAdapter();
        }
    }


    public class MyExpandableAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<ShoppingCartGoodsBean.DatasBean.CartListBean> cart_list;

        public MyExpandableAdapter(Context context, List<ShoppingCartGoodsBean.DatasBean.CartListBean> cart_list) {
            this.context=context;
            this.cart_list=cart_list;
        }
        //删除条目的方法
        public void removeChildItem(int groupPosition,int childPosition){
            this.cart_list.get(groupPosition).getGoods().remove(childPosition);
            notifyDataSetChanged();
        }
        //删除条目的方法
        public void removeGroupItem(int groupPosition){
            this.cart_list.remove(groupPosition);
            notifyDataSetChanged();
        }
        @Override
        public int getGroupCount() {
            return cart_list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return cart_list.get(groupPosition).getGoods().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return cart_list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return cart_list.get(groupPosition).getGoods().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
        //一级列表的视图
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            //加载布局
            View view = View.inflate(context, R.layout.shoppingcart_firstitem, null);
            //查找组件
            CheckBox cb_parent = (CheckBox) view.findViewById(R.id.cb_parent);
            TextView tv_parentName = (TextView) view.findViewById(R.id.tv_parentName);
            //设置值
            tv_parentName.setText(cart_list.get(groupPosition).getStore_name());
            if(cart_list.get(groupPosition).isAllCheck()){
                cb_parent.setChecked(true);
            }else{
                cb_parent.setChecked(false);
            }
            cb_parent.setOnClickListener(new OnGroupCheckListener(groupPosition,cb_parent));
            return view;
        }
        //二级列表的视图
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            //加载二级布局
            View view = View.inflate(context, R.layout.shoppingcart_twoitem, null);
            //查找布局
            ImageView iv_childImage = (ImageView) view.findViewById(R.id.iv_childImage);
            TextView tv_childName = (TextView) view.findViewById(R.id.tv_childName);
            TextView tv_childPrice = (TextView) view.findViewById(R.id.tv_childPrice);
            TextView tv_childNum = (TextView) view.findViewById(R.id.tv_childNum);
            CheckBox cb_child = (CheckBox) view.findViewById(R.id.cb_child);
            Button bt_childRemove = (Button) view.findViewById(R.id.bt_childRemove);


            //得到当前条目对象
            final ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean goodsBean = cart_list.get(groupPosition).getGoods().get(childPosition);
            //设置值
            Picasso.with(context).load(goodsBean.getGoods_image_url()).into(iv_childImage);
            tv_childName.setText(goodsBean.getGoods_name());
            tv_childPrice.setText("￥ "+goodsBean.getGoods_price());
            tv_childNum.setText("X"+goodsBean.getGoods_num());
            if (goodsBean.isItemCheck()){
                cb_child.setChecked(true);
            }else{
                cb_child.setChecked(false);
            }
            cb_child.setOnClickListener(new OnChildCheckListener(groupPosition,childPosition,cb_child));
            //给字条目删除设置点击监听
            bt_childRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除购物车信息的参数
                    Map<String,String> removeParams=new HashMap<String, String>();
                    removeParams.put("key",API.sp.getString("userKey",""));
                    removeParams.put("cart_id",goodsBean.getCart_id());
                    //进行数据请求
                    OKHttp3Utils.doPost(API.REMOVE_SHOPPING_CART, removeParams, new GsonObjectCallback<CancelBean>() {
                        @Override
                        public void onUi(CancelBean cancelBean) {
                            if(cancelBean.getCode()==200){
                                //请求成功，删除界面的提哦啊吗
                                removeChildItem(groupPosition,childPosition);
                                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                if(cart_list.get(groupPosition).getGoods().size()==0){
                                    removeGroupItem(groupPosition);
                                }
                                //判断是否有值
                                if (cart_list.size()==0) {
                                    //没有值将列表和总价隐藏，显示提示语句
                                    elv.setVisibility(View.GONE);
                                    linear_total.setVisibility(View.GONE);
                                    tv_tiShi.setVisibility(View.VISIBLE);
                                    return;
                                }
                            }
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {

                        }
                    });
                }
            });

            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
    //一级监听
    private class OnGroupCheckListener implements View.OnClickListener{
        int groupPosition;
        CheckBox cb_parent;

        public OnGroupCheckListener(int groupPosition, CheckBox cb_parent) {
            this.groupPosition = groupPosition;
            this.cb_parent = cb_parent;
        }

        @Override
        public void onClick(View v) {
            //全选状态
            if(((CheckBox)v).isChecked()){
                //全选选中
                setCheck(true);
            }else{
                //全选未选中
                setCheck(false);
                cb_total.setChecked(false);
            }
            notifyCheckAdapter();
        }

        private void setCheck(boolean b) {
            ShoppingCartGoodsBean.DatasBean.CartListBean cartListBean = scgb.getDatas().getCart_list().get(groupPosition);
            List<ShoppingCartGoodsBean.DatasBean.CartListBean> cart_list1 = scgb.getDatas().getCart_list();
            //一级状态
            cartListBean.setAllCheck(b);
            //全选状态
            int num=0;
            for (int i=0;i<cart_list1.size();i++){
                boolean allCheck = cart_list1.get(i).isAllCheck();
                if(!allCheck){
                    num++;
                }
                if(num==0){
                    cb_total.setChecked(true);
                }else{
                    cb_total.setChecked(false);
                }
            }
            //二级状态
            List<ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean> goods = cartListBean.getGoods();
            for (ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean g:goods){
                g.setItemCheck(b);
            }

        }
    }
    //二级监听
    private class OnChildCheckListener implements View.OnClickListener{
        int groupPosition;
        int childPosition;
        CheckBox cb_child;

        public OnChildCheckListener(int groupPosition, int childPosition, CheckBox cb_child) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
            this.cb_child = cb_child;
        }

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()){
                cart_list.get(groupPosition).getGoods().get(childPosition).setItemCheck(true);
            }else {
                cart_list.get(groupPosition).getGoods().get(childPosition).setItemCheck(false);
            }
            //设置联动效果
            setParentCheckFlag();
            //检测状态
            int num=0;
            for(int i=0;i<cart_list.size();i++){
                boolean allCheck = cart_list.get(i).isAllCheck();
                if(!allCheck){
                    num++;
                }
            }
            if(num==0){
                cb_total.setChecked(true);
            }else{
                cb_total.setChecked(false);
            }

        }

        private void setParentCheckFlag() {
            ShoppingCartGoodsBean.DatasBean.CartListBean cartListBean = cart_list.get(groupPosition);
            List<ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean> goodsBeen = cartListBean.getGoods();
            //遍历子集合
            for (int i=0;i<goodsBeen.size();i++){
                if(!goodsBeen.get(i).isItemCheck()){
                    cartListBean.setAllCheck(false);
                    //刷新适配器
                    notifyCheckAdapter();
                    return;
                }
                if(i==goodsBeen.size()-1){
                    cartListBean.setAllCheck(true);
                    notifyCheckAdapter();
                    return;
                }
            }
            sum();
        }
    }
    //刷新适配器界面
    private void notifyCheckAdapter() {
        sum();
        elv.setAdapter(expandableAdapter);
        int count = elv.getCount();
        for (int i=0;i<count;i++){
            elv.expandGroup(i);
        }
    }

    private void sum() {
        int num=0;
        int price=0;
        List<ShoppingCartGoodsBean.DatasBean.CartListBean> cart_list = scgb.getDatas().getCart_list();
        for(ShoppingCartGoodsBean.DatasBean.CartListBean group:cart_list){
            for(ShoppingCartGoodsBean.DatasBean.CartListBean.GoodsBean goods:group.getGoods()){
                if(goods.isItemCheck()){
                    num+=Integer.parseInt(goods.getGoods_num());
                    double v = Double.parseDouble(goods.getGoods_price());
                    price+=(int)v;
                }
            }
        }
        tv_total.setText("￥"+price);
        bt_total.setText("结算("+num+")");
    }
}
