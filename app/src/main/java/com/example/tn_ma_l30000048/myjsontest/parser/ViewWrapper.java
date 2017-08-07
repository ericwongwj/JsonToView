package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;

import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/7.
 */

public class ViewWrapper {
    protected ViewGroupWrapper mParent;
    protected Map<String, Object> mDataMap;//请求到的所有数据 每一个vg都持有对请求到的那一份的引用
    protected View jsonView;
    protected Context mContext;
    int tagId;//可能可以设置这个？

    public ViewWrapper(View v) {
        this(v, null);
    }

    public ViewWrapper(Context context) {
        mContext = context;
    }

    public ViewWrapper(View v, ViewGroupWrapper parent) {
        this(v, parent, null);
    }

    public ViewWrapper(View v, ViewGroupWrapper parent, Map<String, Object> dataMap) {
        jsonView = v;
        mDataMap = dataMap;
        mContext = v.getContext();
        mParent = parent;
    }

    //通常数据是由这里加载进来的
    public void setDataMap(Map<String, Object> mDataMap) {
        this.mDataMap = mDataMap;
    }

    public View getJsonView() {
        return jsonView;
    }

    public Context getContext() {
        return mContext;
    }
}
