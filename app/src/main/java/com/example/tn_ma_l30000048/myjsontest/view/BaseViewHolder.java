package com.example.tn_ma_l30000048.myjsontest.view;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
//        if(((String)itemView.getTag()).equals("cell")){
//            //首先判断是否是viewgroup 重点是 如果把内部的view拿出来 能够在后面进行set
//            //viewholder中应当有列表每个item的所有子view的具体内容
//            if(itemView instanceof ViewGroup){
//
//            }
//        }else if(itemView.getTag().equals("header")){
//
//        }else if(itemView.getTag().equals("footer")){
//
//        }else if(itemView.getTag().equals("status")){
//
//        }
    }

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        onInitializeView();
    }

    public void onInitializeView() {

    }

    public <T extends View> T findViewById(@IdRes int resId) {
        return (T) itemView.findViewById(resId);
    }

    //这里的设计算不上好
    public void setData(final T data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClick(data);
            }
        });
    }

    protected void onItemViewClick(T data) {

    }

}
