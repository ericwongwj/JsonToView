package com.example.tn_ma_l30000048.myjsontest.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.tn_ma_l30000048.myjsontest.parser.JsonRoot;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    View mItemView;
    JsonRoot mViewRoot;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mViewRoot = null;
        this.mItemView = itemView;
    }

    public BaseViewHolder(JsonRoot viewRoot) {
        super(viewRoot.getJsonView());
        System.out.println("new base view holder");
        this.mViewRoot = viewRoot;
        this.mItemView = viewRoot.getJsonView();
    }

    public <T extends View> T findCellViewByNodeName(String name) {
        T subView = null;
        if (mItemView instanceof ViewGroup) {
//            subView = (T) mViewRoot.getRootViewWrapper().findViewByNodeName(name);
        }
        return subView;
    }

    //这里的设计算不上好
    public void setOnClickListener(final T data) {
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClick(data);
            }
        });
    }

    //这个要怎么实现？
    public void setData(T data) {

    }

    protected void onItemViewClick(T data) {

    }

}
