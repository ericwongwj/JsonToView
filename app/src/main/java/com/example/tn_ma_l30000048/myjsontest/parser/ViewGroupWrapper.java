package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

//普通界面由viewroup进行子view的数据的设置 而列表的item本身也是一个根布局
public class ViewGroupWrapper extends ViewWrapper {

    protected Map<String, View> mViewNameMap = new HashMap<>();//nodeName到子view的map
    protected SparseArray<View> mViewTagMap = new SparseArray<>();//tag到子view的map
    protected List<ViewWrapper> subViewWrappers = new ArrayList<>();

    public ViewGroupWrapper(Context context) {
        super(context);//for virtual node
    }

    //每个viewGroup都有自己的viewMap
    public ViewGroupWrapper(View v, ViewGroupWrapper parent) {
        super(v, parent);
        initViewMap();
    }

    //recyclerview的处理？四个类型的cell？
    //目前这个函数是一个viewgroup下所有view的信息
    public void initViewMap() {
        if (jsonView == null)
            return;

        Queue<View> queue = new LinkedList<>();
        queue.offer(jsonView);
        System.out.println("initViewMap");
        while (!queue.isEmpty()) {
            View v = queue.poll();
            if (v.getTag() == null)
                continue;
            mViewTagMap.put(v.getId(), v);
            mViewNameMap.put(v.getTag().toString(), v);
            System.out.println("nodeName:" + v.getTag() + " " + v.getClass().getSimpleName());
            if (v instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                    View subView = ((ViewGroup) v).getChildAt(i);
                    queue.offer(subView);
                }
            }
        }
    }

    public View findViewByTagId(int tagId) {
        return findViewByTagId(tagId, null);
    }

    public View findViewByTagId(int tagId, Class<? extends View> viewClazz) {
        if (viewClazz == null)
            return mViewTagMap.get(tagId);
        return viewClazz.cast(mViewTagMap.get(tagId));
    }

    //需要自己根据nodeName返回相应的view 并且调用者应该知道view的具体类型
    public View findViewByNodeName(String nodeName) {
        return findViewByNodeName(nodeName, null);
    }

    public View findViewByNodeName(String nodeName, Class<? extends View> viewClazz) {
        if (viewClazz == null)
            return mViewNameMap.get(nodeName);
        return viewClazz.cast(mViewNameMap.get(nodeName));
    }

    public void appendView(ViewWrapper vm) {
        subViewWrappers.add(vm);
    }


}