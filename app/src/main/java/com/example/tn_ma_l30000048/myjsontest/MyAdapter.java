package com.example.tn_ma_l30000048.myjsontest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<String> mData;

    private MyAdapter.OnItemClickListener onItemClickListener;

    public MyAdapter(List<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addNewItem() {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(0, "new Item");
        notifyItemInserted(0);
    }

    public void deleteItem() {
        if(mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(0);
        notifyItemRemoved(0);
    }

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        System.out.println("onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        System.out.println("onBindViewHolder " + position);
        holder.mTv.setText(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                    mData.remove(0);
                    mData.remove(1);
                    mData.add("aaaaaa");
                    mData.add("bbbbbb");
                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            System.out.println("init View holder");
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
