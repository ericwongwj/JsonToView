package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;

/**
 * Created by tn-ma-l30000048 on 17/8/7.
 */

public class ViewWrapper {
    protected View jsonView;
    protected int tagId;
    protected String nodeName;
    protected Context mContext;
    protected AtomicData dataSource;

    public ViewWrapper() {

    }

    public ViewWrapper(Context context) {
        this.mContext = context;
    }

    public ViewWrapper(View v) {
        jsonView = v;
        mContext = v.getContext();
    }


    public ViewWrapper(View v, int tag, String name) {
        jsonView = v;
        tagId = tag;
        nodeName = name;
        mContext = v.getContext();
    }

    public View getJsonView() {
        return jsonView;
    }

    public void setJsonView(View jsonView) {
        this.jsonView = jsonView;
        this.mContext = jsonView.getContext();
    }

    public Context getContext() {
        return mContext;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public AtomicData getDataSource() {
        return dataSource;
    }

    public void setDataSource(AtomicData dataSource) {
        this.dataSource = dataSource;
    }
}
