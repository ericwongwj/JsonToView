package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

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
    static final String TAG = ViewGroupWrapper.class.getSimpleName() + " ";

    protected Map<String, View> mViewNameMap = new HashMap<>();//nodeName到子view的map
    protected SparseArray<View> mViewTagMap = new SparseArray<>();//tag到子view的map

    protected List<ViewWrapper> mSubViewWrappers = new ArrayList<>();

    ViewGroupWrapper(Context context) {
        super(context);
    }

    ViewGroupWrapper(View v) {
        super(v);
    }

    //recyclerview的处理 四个类型的cell？目前这个函数是一个viewgroup下所有view的信息
    //目前是所有的view的id都不相同
    public void initViewTagMap(ViewWrapper vw) {
        if (mJsonView == null)
            return;

        Queue<ViewWrapper> queue = new LinkedList<>();
        queue.offer(vw);
//        System.out.println(TAG+"initViewTagMap");
        while (!queue.isEmpty()) {
            ViewWrapper v = queue.poll();
//            System.out.println(v.getTagId());
            mViewTagMap.put(v.getTagId(), v.getJsonView());
            //mViewNameMap.put(v.getTag().toString(), v);
//            System.out.println("nodeName:" + v.getTag() + " " + v.getClass().getSimpleName());
            if (v instanceof ViewGroupWrapper) {
                for (int i = 0; i < ((ViewGroupWrapper) v).getSubViewWrappers().size(); i++) {
                    ViewWrapper subView = ((ViewGroupWrapper) v).getSubViewWrappers().get(i);
                    queue.offer(subView);
                }
            }
        }
    }

    protected void layoutViewGroup(int pw, int ph) {
        System.out.println(TAG + "pw:" + pw + " ph:" + ph + " size=" + mSubViewWrappers.size());
        layoutView(pw, ph);//可能会改变
        int width = mJsonView.getLayoutParams().width;
        int height = mJsonView.getLayoutParams().height;

        for (ViewWrapper vw : mSubViewWrappers) {
            if (vw instanceof ViewGroupWrapper)
                ((ViewGroupWrapper) vw).layoutViewGroup(width, height);
            else
                vw.layoutView(width, height);
        }
    }

    public List<ViewWrapper> getSubViewWrappers() {
        return mSubViewWrappers;
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
        mSubViewWrappers.add(vm);
    }


}