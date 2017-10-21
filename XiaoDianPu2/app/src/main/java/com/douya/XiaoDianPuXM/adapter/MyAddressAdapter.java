package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;
import com.douya.XiaoDianPuXM.bean.AddressBean;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/20.
 */

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyAddressViewHolder> {
    private Context context;
    private List<AddressBean.DatasBean.AddressListBean> address_list;

    public MyAddressAdapter(Context context, List<AddressBean.DatasBean.AddressListBean> address_list) {
        this.context = context;
        this.address_list = address_list;
    }

    @Override
    public MyAddressAdapter.MyAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);
        MyAddressViewHolder holder=new MyAddressViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAddressAdapter.MyAddressViewHolder holder, int position) {
        AddressBean.DatasBean.AddressListBean listBean = address_list.get(position);
        holder.addName.setText(listBean.getTrue_name());
        holder.addPhone.setText(listBean.getMob_phone());
        holder.addInfo.setText(listBean.getArea_info());
    }

    @Override
    public int getItemCount() {
        return address_list.size();
    }

    public class MyAddressViewHolder extends RecyclerView.ViewHolder{

        private final TextView addName;
        private final TextView addPhone;
        private final TextView addInfo;

        public MyAddressViewHolder(View itemView) {
            super(itemView);
            addName = (TextView) itemView.findViewById(R.id.tv_addName);
            addPhone = (TextView) itemView.findViewById(R.id.tv_addPhone);
            addInfo = (TextView) itemView.findViewById(R.id.tv_addInfo);
        }
    }
    public interface OnItemClickListener{
        
    }
}
