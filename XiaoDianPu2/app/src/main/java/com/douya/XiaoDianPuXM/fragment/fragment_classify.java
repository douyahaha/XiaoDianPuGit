package com.douya.XiaoDianPuXM.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.adapter.MyFirstTypeAdapter;
import com.douya.XiaoDianPuXM.adapter.MyTwoTypeAdapter;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.FirstTypeBean;
import com.douya.XiaoDianPuXM.bean.TwoTypeBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/3.
 */

public class Fragment_classify extends Fragment {
    private ProgressBar progressClassify;
    private RecyclerView ClassifyTitle;
    private RecyclerView ClassifyContent;
    private FirstTypeBean.DatasBean.ClassListBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        progressClassify = (ProgressBar) view.findViewById(R.id.progressClassify);
        ClassifyTitle = (RecyclerView) view.findViewById(R.id.ClassifyTitle);
        ClassifyContent = (RecyclerView) view.findViewById(R.id.ClassifyContent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        //得到WindowManager
        WindowManager windowManager=getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        //得到屏幕宽
        int width = display.getWidth();
        //将左边的RecycleView设置成屏幕的1/5
        params.width=width*1/5;
        ClassifyTitle.setLayoutParams(params);
        //得到左边的布局并设置
        LinearLayoutManager left=new LinearLayoutManager(getContext());
        ClassifyTitle.setLayoutManager(left);
        //得到左又边的布局并设置
        LinearLayoutManager right=new LinearLayoutManager(getContext());
        ClassifyContent.setLayoutManager(right);
        //请求数据
        getFirstData();
    }
    //请求数据
    public void getFirstData(){
        OKHttp3Utils.doGet(API.TYPE_PATH, new GsonObjectCallback<FirstTypeBean>() {
            @Override
            public void onUi(FirstTypeBean firstTypeBean) {
                progressClassify.setVisibility(View.GONE);
                final List<FirstTypeBean.DatasBean.ClassListBean> list = firstTypeBean.getDatas().getClass_list();
                final MyFirstTypeAdapter myFirstTypeAdapter=new MyFirstTypeAdapter(getContext(),list);
                ClassifyTitle.setAdapter(myFirstTypeAdapter);
                getTwoType("1");
                //给条目设置点击监听
                myFirstTypeAdapter.setOnItemClickListener(new MyFirstTypeAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(int position) {
                        //设置当前条目下标
                        myFirstTypeAdapter.setTagPosition(position);
                        //适配器刷新
                        myFirstTypeAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"下标"+position,Toast.LENGTH_SHORT).show();
                        //获取当前条目对象
                        bean = list.get(position);
                        getTwoType(bean.getGc_id());
                    }

        });
    }

            @Override
            public void onFailed(Call call, IOException e) {
                progressClassify.setVisibility(View.GONE);
                Toast.makeText(getContext(),"小主，请重新加载...",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //请求二级列表的数据
   public void getTwoType(String gc_id){

       OKHttp3Utils.doGet(API.TYPE_PATH+"&gc_id="+gc_id, new GsonObjectCallback<TwoTypeBean>() {

           private List<TwoTypeBean.DatasBean.ClassListBean> list;

           @Override
           public void onUi(TwoTypeBean twoTypeBean) {
               list = twoTypeBean.getDatas().getClass_list();
               MyTwoTypeAdapter adapter=new MyTwoTypeAdapter(getContext(), list);
               ClassifyContent.setAdapter(adapter);
           }

           @Override
           public void onFailed(Call call, IOException e) {

           }
       });
    }
}
