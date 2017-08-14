package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.model.Layout;

/**
 * Created by tn-ma-l30000048 on 17/8/7.
 */

public class ViewWrapper {
    static final String TAG = ViewWrapper.class.getSimpleName() + " ";

    protected View mJsonView;
    protected int tagId;
    protected String nodeName;
    protected Context mContext;
    protected AtomicData dataSource;
    protected Layout mLayout = new Layout();

    public ViewWrapper() {

    }

    public ViewWrapper(Context context) {
        this.mContext = context;
    }

    public ViewWrapper(View v) {
        mJsonView = v;
        mContext = v.getContext();
    }

    public ViewWrapper(View v, int tag, String name) {
        mJsonView = v;
        tagId = tag;
        nodeName = name;
        mContext = v.getContext();
    }

    public void layoutView(int pw, int ph) {
        System.out.println(TAG + "pw:" + pw + " ph:" + ph);
        mLayout.parentWidth = pw;
        mLayout.parentHeight = ph;
        JsonViewUtils.setLayoutParams(mJsonView, mLayout);
    }

    public View getJsonView() {
        return mJsonView;
    }

    public void setJsonView(View mJsonView) {
        this.mJsonView = mJsonView;
        this.mContext = mJsonView.getContext();
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

    public Layout getLayout() {
        return mLayout;
    }

    public void setLayout(Layout layout) {
        this.mLayout = layout;
    }
}
