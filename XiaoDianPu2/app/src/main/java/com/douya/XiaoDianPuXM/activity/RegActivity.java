package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.presenter.RegPresenterImpl;
import com.douya.XiaoDianPuXM.view.RegViewInterface;

public class RegActivity extends AppCompatActivity implements View.OnClickListener,RegViewInterface {

    private ImageView iv_regBack;
    private EditText et_regName;
    private EditText et_regPwd;
    private EditText et_regAgainPwd;
    private EditText et_regEmail;
    private Button bt_regCommit;
    private RegPresenterImpl regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        //初始化RegPresenterImpl对象
        regPresenter = new RegPresenterImpl(this);
    }

    private void initView() {
        iv_regBack = (ImageView) findViewById(R.id.iv_regBack);
        et_regName = (EditText) findViewById(R.id.et_regName);
        et_regPwd = (EditText) findViewById(R.id.et_regPwd);
        et_regAgainPwd = (EditText) findViewById(R.id.et_regAgainPwd);
        et_regEmail = (EditText) findViewById(R.id.et_regEmail);
        bt_regCommit = (Button) findViewById(R.id.bt_regCommit);

        bt_regCommit.setOnClickListener(this);
        iv_regBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regCommit:
                Toast.makeText(this,"点击了注册按钮",Toast.LENGTH_SHORT).show();
                if(regPresenter!=null){
                    regPresenter.regGetData(RegActivity.this,
                            et_regName.getText().toString(),
                            et_regPwd.getText().toString(),
                            et_regAgainPwd.getText().toString(),
                            et_regEmail.getText().toString());
                }

                break;
            case R.id.iv_regBack:
                toBack();
                break;
        }
    }


    @Override
    public void toBack() {
        //跳转到登录页面
        startActivity(new Intent(RegActivity.this,LoginActivity.class));
    }

    @Override
    public void onNameEmpty() {
        et_regName.setError("注册名不能为空");
    }

    @Override
    public void onPwdEmpty() {
        et_regPwd.setError("注册密码不能为空");
    }

    @Override
    public void onAgainPwdEmpty() {
        et_regAgainPwd.setError("请填写再次确定密码");
    }

    @Override
    public void onEmailEmpty() {
        et_regEmail.setError("邮箱不能为空");
    }

    @Override
    public void onRegSucceed() {
        Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
        //跳转到登录页面
        startActivity(new Intent(RegActivity.this,LoginActivity.class));
    }

    @Override
    public void onRegFailed() {
        et_regName.setText("");
        et_regPwd.setText("");
        et_regAgainPwd.setText("");
        et_regEmail.setText("");
    }
}
