package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.bean.SanTypeBean;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/17.
 */

public class MySanTypeAdapter extends RecyclerView.Adapter<MySanTypeAdapter.MyViewHolder>{
    private Context context;
    List<SanTypeBean.DatasBean.ClassListBean> list;
    public MySanTypeAdapter(Context context, List<SanTypeBean.DatasBean.ClassListBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MySanTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.santype_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MySanTypeAdapter.MyViewHolder holder, int position) {
        SanTypeBean.DatasBean.ClassListBean bean = list.get(position);
        holder.tv_sanName.setText(bean.getGc_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_sanName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_sanName = (TextView) itemView.findViewById(R.id.tv_sanName);
        }
    }
}
