package com.douya.XiaoDianPuXM.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.presenter.LoginPresenterImpl;
import com.douya.XiaoDianPuXM.view.LoginViewInterface;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,LoginViewInterface {

    private EditText et_name;
    private EditText et_pwd;
    private TextView login_reg;
    private Button bt_login;
    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //初始化LoginPresenter对象
        loginPresenter = new LoginPresenterImpl(this);
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        login_reg = (TextView) findViewById(R.id.login_reg);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(this);
        login_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                loginPresenter.loginGetData(this,
                        et_name.getText().toString(),
                        et_pwd.getText().toString());
                break;
            case R.id.login_reg:
                //点击新用户注册进行页面跳转
                startActivity(new Intent(LoginActivity.this,RegActivity.class));
                break;
        }
    }


    @Override
    public void onLoginNameEmpty() {
        et_name.setError("用户名不能为空");
    }

    @Override
    public void onLoginPwdEmpty() {
        et_pwd.setError("密码不能为空");
    }

    @Override
    public void onLoginSucceed() {
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this,"请输入正确的用户名或密码",Toast.LENGTH_SHORT).show();
        et_name.setText("");
        et_pwd.setText("");
    }
}
