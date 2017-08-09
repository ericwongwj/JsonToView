package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;

import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;
import com.example.tn_ma_l30000048.myjsontest.view.RecyclerAdapter;

/**
 * Created by tn-ma-l30000048 on 17/8/9.
 */

public class CollectionViewWrapper extends ViewWrapper {
    RecyclerAdapter adapter;
    private String cellName;
    private String headName;
    private String footName;
    private String insertName;

    public CollectionViewWrapper() {
        super();
    }

    public CollectionViewWrapper(Context context) {
        super(context);
    }

    void buildAdapter() {

    }

    void initRecyclerView() {
        MyRecyclerView myRecyclerView = (MyRecyclerView) mJsonView;
        myRecyclerView.setAdapter(adapter);
    }

    public void setAdapter(RecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public void setFoot(String foot) {
        this.footName = foot;
    }

    public void setHead(String head) {
        this.headName = head;
    }

    public void setInsert(String insert) {
        this.insertName = insert;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }
}
