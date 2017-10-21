package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.adapter.MyTuiJianadapter;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.CancelBean;
import com.douya.XiaoDianPuXM.bean.GoodsXQBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class GoodsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_goodsImage;
    private TextView tv_goods_name;
    private LinearLayout linear_fenXiang;
    private TextView tv_price;
    private TextView tv_kuaidi;
    private TextView tv_shifouyouhuo;
    private TextView tv_diqu;
    private RecyclerView recycler_tuijian;
    private WebView web_goods;
    private Button bt_shooping;
    private Button bt_pay;
    private ImageView iv_goodsBack;
    private TextView tv_goodsguangGao;
    private TextView tv_dianPu;
    private TextView tv_miaoShu;
    private TextView tv_fuWu;
    private TextView tv_wuLiu;
    private PopupWindow popupWindow;
    private View view;
    private ImageView iv_popGoodImage;
    private TextView tv_popGoodsNmae;
    private TextView tv_popGoodsPrice;
    private TextView tv_popGoodStorage;
    private ImageView iv_popJia;
    private TextView tv_popCount;
    private ImageView iv_popJian;
    private Button bt_popShoppingCart;
    private Button bt_popPay;
    private int count=1;
    private GoodsXQBean.DatasBean.GoodsInfoBean goods_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initView();
        //获取Intent对象
        Intent intent = getIntent();
        //获取值
        final String goods_id = intent.getStringExtra("goods_id");
        //进行数据请求
        OKHttp3Utils.doGet(API.SELECT_GOODS_PATH + "&goods_id=" + goods_id, new GsonObjectCallback<GoodsXQBean>() {
            @Override
            public void onUi(GoodsXQBean goodsXQBean) {
                goods_info = goodsXQBean.getDatas().getGoods_info();
                //加载商品图片
                Picasso.with(GoodsActivity.this).load(goodsXQBean.getDatas().getGoods_image()).into(iv_goodsImage);
                Picasso.with(GoodsActivity.this).load(goodsXQBean.getDatas().getGoods_image()).into(iv_popGoodImage);
                //加载商品名称
                tv_goods_name.setText(goods_info.getGoods_name());
                tv_popGoodsNmae.setText(goods_info.getGoods_name());
                tv_popGoodsPrice.setText("￥ "+goods_info.getGoods_price());
                tv_popGoodStorage.setText("库存 ：" + goods_info.getGoods_storage());
                //商品价格
                tv_price.setText("￥ " + goods_info.getGoods_price() + "-" + goods_info.getGoods_marketprice());
                //获取快递对象
                GoodsXQBean.DatasBean.GoodsHairInfoBean goods_hair_info = goodsXQBean.getDatas().getGoods_hair_info();
                //设置快递
                tv_kuaidi.setText(goods_hair_info.getContent());
                //是否有货
                tv_shifouyouhuo.setText(goods_hair_info.getIf_store_cn());
                //地区
                tv_diqu.setText(goods_hair_info.getArea_name());
                //广告
                tv_goodsguangGao.setText(goods_info.getGoods_jingle());
                //获取具体信息的对象
                GoodsXQBean.DatasBean.StoreInfoBean store_info = goodsXQBean.getDatas().getStore_info();
                //设置商城
                tv_dianPu.setText(store_info.getStore_name());
                //设置描述
                tv_miaoShu.setText("描述 ：" + store_info.getStore_credit().getStore_deliverycredit().getCredit() + "分");
                tv_fuWu.setText("服务 ：" + store_info.getStore_credit().getStore_servicecredit().getCredit() + "分");
                tv_wuLiu.setText("无流 ：" + store_info.getStore_credit().getStore_desccredit().getCredit() + "分");
                //获取推荐集合
                List<GoodsXQBean.DatasBean.GoodsCommendListBean> goods_commend_list = goodsXQBean.getDatas().getGoods_commend_list();
                //创建适配器
                MyTuiJianadapter myTuiJianadapter = new MyTuiJianadapter(GoodsActivity.this, goods_commend_list);
                recycler_tuijian.setAdapter(myTuiJianadapter);
                //WebView加载商品详细信息
                WebSettings settings = web_goods.getSettings();
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                settings.setJavaScriptEnabled(true);
                web_goods.loadUrl(API.GOODS_PATH + "&goods_id=" + goods_id);
                web_goods.setWebViewClient(new WebViewClient());
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    private void initView() {
        iv_goodsImage = (ImageView) findViewById(R.id.iv_goodsImage);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        linear_fenXiang = (LinearLayout) findViewById(R.id.linear_fenXiang);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_kuaidi = (TextView) findViewById(R.id.tv_kuaidi);
        tv_shifouyouhuo = (TextView) findViewById(R.id.tv_shifouyouhuo);
        tv_diqu = (TextView) findViewById(R.id.tv_diqu);
        recycler_tuijian = (RecyclerView) findViewById(R.id.recycler_tuijian);
        web_goods = (WebView) findViewById(R.id.web_goods);
        bt_shooping = (Button) findViewById(R.id.bt_shooping);
        bt_pay = (Button) findViewById(R.id.bt_pay);
        iv_goodsBack = (ImageView) findViewById(R.id.iv_goodsBack);
        bt_shooping.setOnClickListener(this);
        bt_pay.setOnClickListener(this);
        iv_goodsBack.setOnClickListener(this);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_tuijian.setLayoutManager(layoutManager);
        tv_goodsguangGao = (TextView) findViewById(R.id.tv_goodsguangGao);
        tv_dianPu = (TextView) findViewById(R.id.tv_dianPu);
        tv_miaoShu = (TextView) findViewById(R.id.tv_miaoShu);
        tv_fuWu = (TextView) findViewById(R.id.tv_fuWu);
        tv_wuLiu = (TextView) findViewById(R.id.tv_wuLiu);
        //加载PopupWindow布局
        view = LayoutInflater.from(this).inflate(R.layout.shoppingcart_item, null);
        //初始化PopupWindow
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, 400);
        //设置点击PopupWindow外部，关闭PopupWindow
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        //查找PopupWindow中的布局

        iv_popGoodImage = (ImageView) view.findViewById(R.id.iv_popGoodImage);
        tv_popGoodsNmae = (TextView) view.findViewById(R.id.tv_popGoodsNmae);
        tv_popGoodsPrice = (TextView) view.findViewById(R.id.tv_popGoodsPrice);
        tv_popGoodStorage = (TextView) view.findViewById(R.id.tv_popGoodStorage);
        iv_popJia = (ImageView) view.findViewById(R.id.iv_popJia);
        tv_popCount = (TextView) view.findViewById(R.id.tv_popCount);
        iv_popJian = (ImageView) view.findViewById(R.id.iv_popJian);
        iv_popJia.setOnClickListener(this);
        iv_popJian.setOnClickListener(this);
        bt_popShoppingCart = (Button) view.findViewById(R.id.bt_popShoppingCart);
        bt_popShoppingCart.setOnClickListener(this);
        bt_popPay = (Button) view.findViewById(R.id.bt_popPay);
        bt_popPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_shooping:
                if (API.sp.getBoolean("isLogin", false)) {
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.bt_pay:
                if (API.sp.getBoolean("isLogin", false)) {
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.iv_goodsBack:
                finish();
                break;
            case R.id.iv_popJia:
                if(count==5){
                    Toast.makeText(this,"最多添加 5 条数据",Toast.LENGTH_SHORT).show();
                }else{
                    count++;
                    tv_popCount.setText(count+"");
                }

                break;
            case R.id.iv_popJian:

                if(count==1){
                    Toast.makeText(this,"至少要保存 1 条数据",Toast.LENGTH_SHORT).show();
                }else{
                    count--;
                    tv_popCount.setText(count+"");
                }

                break;
            case R.id.bt_popShoppingCart:
                if (API.sp.getBoolean("isLogin", false)) {
                    String goodsCount = tv_popCount.getText().toString();
                    getServerShoppingCart(goodsCount);
                    popupWindow.dismiss();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.bt_popPay:
                if (API.sp.getBoolean("isLogin", false)) {
                    popupWindow.dismiss();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    private void getServerShoppingCart(String goodsCount) {
        Map<String,String> cartParams=new HashMap<>();
        cartParams.put("key",API.sp.getString("userKey",""));
        cartParams.put("goods_id",goods_info.getGoods_id());
        cartParams.put("quantity",goodsCount);
        OKHttp3Utils.doPost(API.POST_SHOPPING_CART, cartParams, new GsonObjectCallback<CancelBean>() {
            @Override
            public void onUi(CancelBean cancelBean) {
                if(cancelBean.getCode()==200){
                    Toast.makeText(GoodsActivity.this,"添加成功``V``",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }


}
