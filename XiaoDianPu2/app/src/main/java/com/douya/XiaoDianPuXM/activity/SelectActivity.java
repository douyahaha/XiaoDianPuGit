package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.adapter.MyHistoryAdapter;
import com.douya.XiaoDianPuXM.app.MyApp;
import com.douya.XiaoDianPuXM.utils.SelectHistoryUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SelectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_selectBack;
    private EditText et_goodsName;
    private Button bt_select;
    private LinearLayout linear_history;
    private RecyclerView lv_history;
    private TextView tv_history;
    private Intent intent;
    //定义最对显示几条历史记录
    public static final int HISTORY_MAX=5;
    //定义一个Map集合用来存储，获取到的历史记录
    private Map<String,String> historyAll;
    //定义List集合，用来存储获取的内容
    private List<String> historyContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        MyApp.getInstance().addActivity(this);
        initView();
        //当EditText为空时，展示历史记录
        if(TextUtils.isEmpty(et_goodsName.getText().toString())){
            //展示历史记录
            showSelectHistory();
        }
        //EditText监听
        et_goodsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {//索索框内容为空  显示历史记录
                    showSelectHistory();
                }
            }
        });
        //搜索按钮点击监听
        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_goodsName.getText().toString())) {
                    saveSearchHistory(et_goodsName.getText().toString());
                }
                //点击搜索 跳转搜索结果
                intent = new Intent(SelectActivity.this, SelectResultActivity.class);
                //根据输入的内容传值
                if (et_goodsName.getText().toString().contains("劳力士")) {
                    intent = new Intent(SelectActivity.this,SelectResultActivity.class);
                    intent.putExtra("goodsName","劳力士");
                    startActivity(intent);
                } else {

                    intent.putExtra("goodsName",et_goodsName.getText().toString());
                    startActivity(intent);
                }
            }
        });
        //mFooterView点击监听
        tv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除历史记录
                clearsearchHistory();
            }
        });
    }

    //保存搜索记录的方法
    private void saveSearchHistory(String keyWords) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        Map<String, String> historys = (Map<String, String>) SelectHistoryUtils.getAll(SelectActivity.this);
        for (Map.Entry<String, String> entry : historys.entrySet()) {
            if (keyWords.equals(entry.getValue())) {
                SelectHistoryUtils.remove(SelectActivity.this, entry.getKey());
            }
        }
        SelectHistoryUtils.put(SelectActivity.this, "" + mFormat.format(System.currentTimeMillis()), keyWords);
    }
    private void showSelectHistory() {
        //将获取的值转换成Map集合
        historyAll=SelectHistoryUtils.getAll(SelectActivity.this);
        Object[] keys = historyAll.keySet().toArray();
        //给key进行排序
        Arrays.sort(keys);
        //得到数组的长度
        int length = keys.length;
        //判断当前的长度是否大于最大的长度，大于最大长度就用最大长度进行遍历，避免数组越界
        int historyLength=length>HISTORY_MAX ? HISTORY_MAX :length;
        //初始化集合用来存得到的内容
        historyContext =new ArrayList<>();
        for(int i=1;i<=historyLength;i++){
            historyContext.add(historyAll.get(keys[length-i]));
        }
        MyHistoryAdapter adapter=new MyHistoryAdapter(this,historyContext);
        lv_history.setAdapter(adapter);
        adapter.setOnOnItemClickListener(new MyHistoryAdapter.OnItemClickListener() {



            @Override
            public void onItemClick(int position) {
                String content = historyContext.get(position);
                if(content.contains("劳力士")){
                    intent = new Intent(SelectActivity.this,SelectResultActivity.class);
                    intent.putExtra("goodsName","劳力士");
                    startActivity(intent);
                }else{
                    intent = new Intent(SelectActivity.this,SelectResultActivity.class);
                    intent.putExtra("goodsName",content);
                    startActivity(intent);
                }
            }
        });
        linear_history.setVisibility(0!=historyContext.size()?View.VISIBLE:View.GONE);
        tv_history.setVisibility(0!=historyContext.size()?View.VISIBLE:View.GONE);
    }
    //清楚历史记录
    private void clearsearchHistory() {
        SelectHistoryUtils.clear(this);
        historyContext.clear();
        lv_history.setAdapter(new MyHistoryAdapter(this,historyContext));

        //如果size不为0 显示footerview
        linear_history.setVisibility(0!=historyContext.size()?View.VISIBLE:View.GONE);
        tv_history.setVisibility(0!=historyContext.size()?View.VISIBLE:View.GONE);
    }
    private void initView() {
        iv_selectBack = (ImageView) findViewById(R.id.iv_selectBack);
        et_goodsName = (EditText) findViewById(R.id.et_goodsName);
        bt_select = (Button) findViewById(R.id.bt_select);

        iv_selectBack.setOnClickListener(this);
        linear_history = (LinearLayout) findViewById(R.id.linear_history);
        linear_history.setOnClickListener(this);
        iv_selectBack.setOnClickListener(this);
        lv_history = (RecyclerView) findViewById(R.id.lv_history);
        tv_history = (TextView) findViewById(R.id.tv_history);
        tv_history.setOnClickListener(this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        lv_history.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_selectBack:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.getInstance().removeActivity(this);
    }
}
