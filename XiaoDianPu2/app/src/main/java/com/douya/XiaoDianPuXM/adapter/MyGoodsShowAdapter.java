package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.bean.GoodsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/18.
 */

public class MyGoodsShowAdapter extends RecyclerView.Adapter<MyGoodsShowAdapter.MyGoodsShowViewHolder>{
    private Context context;
    private List<GoodsBean.DatasBean.GoodsListBean> list;

    public MyGoodsShowAdapter(Context context, List<GoodsBean.DatasBean.GoodsListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyGoodsShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goodsshow_item, parent, false);
        final MyGoodsShowViewHolder holder=new MyGoodsShowViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(holder.getLayoutPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyGoodsShowViewHolder holder, int position) {
        GoodsBean.DatasBean.GoodsListBean bean = list.get(position);
        holder.tv_goods_jingle.setText(bean.getGoods_jingle());
        holder.tv_goodsName.setText(bean.getGoods_name());
        holder.tv_store_name.setText(bean.getStore_name());
        Picasso.with(context).load(bean.getGoods_image_url()).into(holder.iv_goodsShow);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyGoodsShowViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_goodsShow;
        private final TextView tv_goods_jingle;
        private final TextView tv_goodsName;
        private final TextView tv_store_name;

        public MyGoodsShowViewHolder(View itemView) {
            super(itemView);
            iv_goodsShow = (ImageView) itemView.findViewById(R.id.iv_goodsShow);
            tv_goods_jingle = (TextView) itemView.findViewById(R.id.tv_goods_jingle);
            tv_goodsName = (TextView) itemView.findViewById(R.id.tv_goodsName);
            tv_store_name = (TextView) itemView.findViewById(R.id.tv_store_name);
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
