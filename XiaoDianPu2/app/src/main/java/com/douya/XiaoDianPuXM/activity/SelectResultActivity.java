package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.adapter.MyGoodsShowAdapter;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.app.MyApp;
import com.douya.XiaoDianPuXM.bean.GoodsBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class SelectResultActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_selectResultBack;
    private EditText et_goodsNameResult;
    private RecyclerView recycler_showGoods;
    private TextView tv_showGoodsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_result);
        MyApp.getInstance().addActivity(this);
        initView();
        Intent intent = getIntent();
        String goodsName = intent.getStringExtra("goodsName");
        if (!goodsName.contains("劳力士")) {
            et_goodsNameResult.setHint(goodsName);
            tv_showGoodsName.setVisibility(View.VISIBLE);
            recycler_showGoods.setVisibility(View.GONE);
            return;
        }
        OKHttp3Utils.doGet(API.SELECT_PATH, new GsonObjectCallback<GoodsBean>() {
            @Override
            public void onUi(GoodsBean goodsBean) {
                final List<GoodsBean.DatasBean.GoodsListBean> goods_list = goodsBean.getDatas().getGoods_list();
                MyGoodsShowAdapter adapter=new MyGoodsShowAdapter(SelectResultActivity.this,goods_list);
                recycler_showGoods.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyGoodsShowAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        GoodsBean.DatasBean.GoodsListBean bean = goods_list.get(position);
                        Intent intent1=new Intent(SelectResultActivity.this,GoodsActivity.class);
                        intent1.putExtra("goods_id",bean.getGoods_id());
                        startActivity(intent1);
                    }
                });
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    private void initView() {
        iv_selectResultBack = (ImageView) findViewById(R.id.iv_selectResultBack);
        et_goodsNameResult = (EditText) findViewById(R.id.et_goodsNameResult);
        recycler_showGoods = (RecyclerView) findViewById(R.id.recycler_showGoods);
        tv_showGoodsName = (TextView) findViewById(R.id.tv_showGoodsName);

        iv_selectResultBack.setOnClickListener(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recycler_showGoods.setLayoutManager(layoutManager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_selectResultBack:
                MyApp.removeAllActivity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.getInstance().removeActivity(this);
    }
}
