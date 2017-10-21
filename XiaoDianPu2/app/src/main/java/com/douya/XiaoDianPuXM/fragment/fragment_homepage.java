package com.douya.XiaoDianPuXM.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.activity.SelectActivity;

/**
 * Created by 杨圆圆 on 2017/10/3.
 */

public class Fragment_homepage extends Fragment {
    private ImageView iv_sao;
    private TextView tv_tao;
    private ImageView iv_ma;
    private RecyclerView rlv_homepage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        initView(view);
        iv_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_tao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SelectActivity.class));
            }
        });
        return view;
    }

    private void initView(View view) {
        iv_sao = (ImageView) view.findViewById(R.id.iv_sao);
        tv_tao = (TextView) view.findViewById(R.id.tv_tao);
        iv_ma = (ImageView) view.findViewById(R.id.iv_ma);
        rlv_homepage = (RecyclerView) view.findViewById(R.id.rlv_homepage);
    }
}
