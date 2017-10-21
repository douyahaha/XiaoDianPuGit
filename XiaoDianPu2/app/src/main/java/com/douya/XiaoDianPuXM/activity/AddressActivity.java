package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.adapter.MyAddressAdapter;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.AddressBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class AddressActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_addressBack;
    private ImageView iv_addressJia;
    private RecyclerView recycler_address;
    private TextView tv_addressTiShi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> addressParams=new HashMap<>();
        addressParams.put("key", API.sp.getString("userKey",""));
        OKHttp3Utils.doPost(API.GET_ADDRESS, addressParams, new GsonObjectCallback<AddressBean>() {
            @Override
            public void onUi(AddressBean addressBean) {
                List<AddressBean.DatasBean.AddressListBean> address_list = addressBean.getDatas().getAddress_list();
                if(address_list.size()==0){
                    recycler_address.setVisibility(View.GONE);
                    tv_addressTiShi.setVisibility(View.VISIBLE);
                    return;
                }
                recycler_address.setVisibility(View.VISIBLE);
                tv_addressTiShi.setVisibility(View.GONE);
                MyAddressAdapter addressAdapter=new MyAddressAdapter(AddressActivity.this,address_list);
                recycler_address.setAdapter(addressAdapter);

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    private void initView() {
        iv_addressBack = (ImageView) findViewById(R.id.iv_addressBack);
        iv_addressJia = (ImageView) findViewById(R.id.iv_addressJia);
        recycler_address = (RecyclerView) findViewById(R.id.recycler_address);
        tv_addressTiShi = (TextView) findViewById(R.id.tv_addressTiShi);

        iv_addressBack.setOnClickListener(this);
        iv_addressJia.setOnClickListener(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recycler_address.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_addressBack:
                finish();
                break;
            case R.id.iv_addressJia:
                startActivity(new Intent(this,AddressRequestActivity.class));
                break;
        }
    }
}
