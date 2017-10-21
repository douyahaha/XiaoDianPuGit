package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.bean.GoodsXQBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/18.
 */

public class MyTuiJianadapter extends RecyclerView.Adapter<MyTuiJianadapter.MyTuiViewHolder>{
    private Context context;
    private List<GoodsXQBean.DatasBean.GoodsCommendListBean> list;

    public MyTuiJianadapter(Context context, List<GoodsXQBean.DatasBean.GoodsCommendListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyTuiJianadapter.MyTuiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tuijian_item, parent, false);
        MyTuiViewHolder holder=new MyTuiViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyTuiJianadapter.MyTuiViewHolder holder, int position) {
        GoodsXQBean.DatasBean.GoodsCommendListBean bean = list.get(position);
        Picasso.with(context).load(bean.getGoods_image_url()).into(holder.iv_tuijian);
        holder.tv_tuiJianName.setText(bean.getGoods_name());
        holder.tv_tuiJianPrice.setText("￥ "+bean.getGoods_promotion_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyTuiViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_tuijian;
        private final TextView tv_tuiJianName;
        private final TextView tv_tuiJianPrice;

        public MyTuiViewHolder(View itemView) {
            super(itemView);
            iv_tuijian = (ImageView) itemView.findViewById(R.id.iv_tuijian);
            tv_tuiJianName = (TextView) itemView.findViewById(R.id.tv_tuiJianName);
            tv_tuiJianPrice = (TextView) itemView.findViewById(R.id.tv_tuiJianPrice);

        }
    }
}
