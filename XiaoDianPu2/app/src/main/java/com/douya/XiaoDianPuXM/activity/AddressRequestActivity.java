package com.douya.XiaoDianPuXM.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.AddBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class AddressRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_addressName;
    private EditText et_addressPhone;
    private EditText et_address;
    private EditText et_addressInfo;
    private Button bt_addressCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_request);
        initView();
    }

    private void initView() {
        et_addressName = (EditText) findViewById(R.id.et_addressName);
        et_addressPhone = (EditText) findViewById(R.id.et_addressPhone);
        et_address = (EditText) findViewById(R.id.et_address);
        et_addressInfo = (EditText) findViewById(R.id.et_addressInfo);
        bt_addressCommit = (Button) findViewById(R.id.bt_addressCommit);
        bt_addressCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_addressCommit:
//                key        对应登陆账户的key值
//                true_name     真实姓名
//                mob_phone     手机号码
//                city_id       城市
//                area_id       省份（地区）
//                address       地址
//                area_info     描述信息  //河南 洛阳市 洛宁县
                String addressName = et_addressName.getText().toString();
                String addressPhone = et_addressPhone.getText().toString();
                String address = et_address.getText().toString();
                String addressInfo = et_addressInfo.getText().toString();
                Map<String,String> addressParams=new HashMap<>();
                addressParams.put("key", API.sp.getString("userKey",""));
                addressParams.put("true_name",addressName);
                addressParams.put("mob_phone",addressPhone);
                addressParams.put("city_id","1");
                addressParams.put("area_id","1");
                addressParams.put("address",address);
                addressParams.put("area_info",addressInfo);
                OKHttp3Utils.doPost(API.POST_ADDRESS, addressParams, new GsonObjectCallback<AddBean>() {
                    @Override
                    public void onUi(AddBean addBean) {
                        if(addBean.getCode()==200){
                            Toast.makeText(AddressRequestActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
                break;
        }

    }
}
