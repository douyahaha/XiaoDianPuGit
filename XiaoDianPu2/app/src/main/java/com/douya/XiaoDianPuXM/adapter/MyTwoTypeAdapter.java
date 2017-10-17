package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.api.API;
import com.douya.XiaoDianPuXM.bean.SanTypeBean;
import com.douya.XiaoDianPuXM.bean.TwoTypeBean;
import com.douya.XiaoDianPuXM.utils.GsonObjectCallback;
import com.douya.XiaoDianPuXM.utils.OKHttp3Utils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/16.
 */

public class MyTwoTypeAdapter extends RecyclerView.Adapter<MyTwoTypeAdapter.MyViewHolder>{
    private Context context;
    private List<TwoTypeBean.DatasBean.ClassListBean> list;

    public MyTwoTypeAdapter(Context context, List<TwoTypeBean.DatasBean.ClassListBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.twotype_item, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TwoTypeBean.DatasBean.ClassListBean bean = list.get(position);
        holder.tv_TwoType.setText(bean.getGc_name());
        OKHttp3Utils.doGet(API.TYPE_PATH+"&gc_id="+bean.getGc_id(), new GsonObjectCallback<SanTypeBean>() {
            @Override
            public void onUi(SanTypeBean sanTypeBean) {
                List<SanTypeBean.DatasBean.ClassListBean> list = sanTypeBean.getDatas().getClass_list();
                MySanTypeAdapter adapter=new MySanTypeAdapter(context,list);
                holder.gv_san.setAdapter(adapter);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_TwoType;
        private final RecyclerView gv_san;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_TwoType = (TextView) itemView.findViewById(R.id.tv_TwoType);
            gv_san = (RecyclerView) itemView.findViewById(R.id.gv_san);
            GridLayoutManager layoutManager=new GridLayoutManager(context,3);
            gv_san.setLayoutManager(layoutManager);
        }
    }

}
