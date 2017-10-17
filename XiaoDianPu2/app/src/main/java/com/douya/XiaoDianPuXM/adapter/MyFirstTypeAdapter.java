package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.bean.FirstTypeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/16.
 */

public class MyFirstTypeAdapter extends RecyclerView.Adapter<MyFirstTypeAdapter.MyViewHolder>{
    private Context context;
    private List<FirstTypeBean.DatasBean.ClassListBean> list;
    public static int tagPosition;

    public static int getTagPosition() {
        return tagPosition;
    }

    public static void setTagPosition(int tagPosition) {
        MyFirstTypeAdapter.tagPosition = tagPosition;
    }
    public MyFirstTypeAdapter(Context context, List<FirstTypeBean.DatasBean.ClassListBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.firsttype_item, parent,false);
        final MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.getLayoutPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FirstTypeBean.DatasBean.ClassListBean bean = list.get(position);
        holder.tv_firstType.setText(bean.getGc_name());
        if(position==getTagPosition()){
            holder.itemView.setBackgroundResource(R.color.colorPblBackground);
        }else{
            holder.itemView.setBackgroundResource(R.color.colorWhite3);
        }
        if(bean.getImage().isEmpty()){
           return;
        }
        Picasso.with(context).load(bean.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.iv_firstType);

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_firstType;
        private final TextView tv_firstType;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_firstType = (ImageView) itemView.findViewById(R.id.iv_FirstType);
            tv_firstType = (TextView) itemView.findViewById(R.id.tv_FirstType);
        }
    }
    //接口回调
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
