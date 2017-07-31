package com.example.tn_ma_l30000048.myjsontest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class MyDynamicAdapter extends RecyclerView.Adapter<MyDynamicAdapter.ViewHolder>{

    private ArrayList<String> mData;
    private Context context;
    OnItemClickListener onItemClickListener;

    public MyDynamicAdapter(ArrayList<String> data, Context context) {
        this.mData = data;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout container=new RelativeLayout(context);
        container.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
        container.setBackgroundColor(Color.YELLOW);

        ImageView iv=new ImageView(context);
        iv.setImageResource(R.mipmap.ic_launcher_round);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(150,150);
        layoutParams.topMargin=15;
        layoutParams.leftMargin=15;
        iv.setLayoutParams(layoutParams);
        iv.setId(1);

        TextView tv=new TextView(context);
        tv.setText("联系人");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        RelativeLayout.LayoutParams tvLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.leftMargin=20;
        tvLayoutParams.addRule(RelativeLayout.ALIGN_TOP,1);
        tvLayoutParams.topMargin=20;
        tvLayoutParams.addRule(RelativeLayout.RIGHT_OF,1);
        tv.setLayoutParams(tvLayoutParams);

        View line=new View(context);
        line.setBackgroundColor(Color.BLUE);
        RelativeLayout.LayoutParams lineLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        lineLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lineLayoutParams.bottomMargin=13;
        line.setLayoutParams(lineLayoutParams);
        container.addView(tv);
        container.addView(iv);
        container.addView(line);

        return new ViewHolder(container,tv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTv.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener!=null){
                    int pos=holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView,pos);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView, TextView tv) {
            super(itemView);
            mTv=tv;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}

