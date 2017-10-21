package com.douya.XiaoDianPuXM.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douya.XiaoDianPuXM.R;

import java.util.List;

/**
 * Created by 杨圆圆 on 2017/10/18.
 */

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.MyViewHolder> {
    private Context context;
    private List<String> historyContext;

    public MyHistoryAdapter(Context context, List<String> historyContext) {
        this.context = context;
        this.historyContext = historyContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        final MyViewHolder holder=new MyViewHolder(view);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_historyContext.setText(historyContext.get(position));
    }

    @Override
    public int getItemCount() {
        return historyContext.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_historyContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_historyContext = (TextView) itemView.findViewById(R.id.tv_historyContext);
        }
    }
    public OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
