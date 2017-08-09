package com.example.tn_ma_l30000048.myjsontest.parser;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.view.Bean;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;
import com.example.tn_ma_l30000048.myjsontest.view.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonTableView {

    public static final String TAG = JsonTableView.class.getSimpleName() + " ";

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {
        CollectionViewWrapper viewWrapper = new CollectionViewWrapper(jsonRoot.getContext());
        MyRecyclerView myRecyclerView = buildTableView(body, viewWrapper, parentWidth, parentHeight);
        viewWrapper.setJsonView(myRecyclerView);
        return viewWrapper;
    }

    //问题是这个函数build的过程中 可能还要再去请求
    // 在cell和数据都未知的情况下 生成adapter没有意义 在
    public static MyRecyclerView buildTableView(JSONObject body, CollectionViewWrapper viewWrapper, int parentWidth, int parentHeight) {
        System.out.println("----buildViewGroup MyRecyclerView----");
        MyRecyclerView recyclerView = new MyRecyclerView(viewWrapper.getContext());
        JsonViewUtils.setTagToWrapper(body, recyclerView, new ViewWrapper());
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), recyclerView, parentWidth, parentHeight);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewWrapper.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        recyclerView.showSwipeRefreshWithTime(1000);

        setBaseStyle(JsonHelper.getStyles(body), recyclerView, viewWrapper);

        return recyclerView;
    }


    static void buildAdapter(MyRecyclerView recyclerView) {
        RecyclerAdapter<Bean> adapter = new RecyclerAdapter<Bean>(recyclerView.getContext());
//        adapter.setHeader(JsonHelper.readLocalUIJson(context, "TNChatContactHead.json"));
//        adapter.setFooter(JsonHelper.readLocalUIJson(context, "TNChatContactFoot.json"));
//        adapter.setCell(JsonHelper.readLocalUIJson(context, "TNChatContactCell.json"));

    }


    //这只设置listview本身的属性 cell和data的设置都不涉及
    static void setBaseStyle(JSONObject json, MyRecyclerView rv, CollectionViewWrapper viewWrapper) {
        Iterator<String> keys=json.keys();
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("backgroundColor")){
                    String bgColor = json.getString(key);
                    rv.setBackgroundColor(JasonHelper.parseColor(bgColor));
                }else if(key.equalsIgnoreCase("scrollDisabled")) {
                    int scrollDisabled = json.getInt(key);
                    rv.getRecyclerView().setNestedScrollingEnabled(scrollDisabled == 0);
                } else if (key.equalsIgnoreCase("tableStyle")) {
                    setTableStyle(json.getJSONObject(key), rv, viewWrapper);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    static void setTableStyle(JSONObject tableStyle, MyRecyclerView rv, CollectionViewWrapper viewWrapper) {
        Iterator<String> keys=tableStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {//这里有本地/网络IO
                    String cell = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(cell)) {//当创建cell模板时，需要设置containerHeight用于返回相应cell的高度??
                        viewWrapper.setCellName(cell);
                    }
                } else if (key.equalsIgnoreCase("headView")) {
                    String headView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(headView)) {
                        viewWrapper.setHead(headView);
                    }
                } else if (key.equalsIgnoreCase("footView")) {
                    String footView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(footView)) {
                        viewWrapper.setFoot(footView);
                    }
                } else if (key.equalsIgnoreCase("insertViews")) {//改变的时候 notifydatasetchange
                    JSONArray insertViews = tableStyle.getJSONArray(key);
                    List<JSONObject> inserViews = new ArrayList<>();
                    for (int i = 0; i < insertViews.length(); i++) {
                        JSONObject insertView = insertViews.getJSONObject(i);
                        String templateName = insertView.getString("templateName");
                    }
                }
                if (key.equalsIgnoreCase("dataPath")) {
                    JSONArray jsonArray = tableStyle.getJSONArray(key);
                    AtomicData dataSource = JsonViewUtils.parseDataSource(jsonArray);
                    viewWrapper.setDataSource(dataSource);
                } else if (key.equalsIgnoreCase("enablePullUpToLoadMore")) {
                    if (tableStyle.getInt(key) == 0)
                        rv.setLoadMoreAble(false);
                    else rv.setLoadMoreAble(true);
                } else if (key.equalsIgnoreCase("enablePullDownToRefresh")) {
                    if (tableStyle.getInt(key) == 0)
                        rv.getSwipeRefreshLayout().setEnabled(false);
                    else rv.getSwipeRefreshLayout().setEnabled(true);
                } else if (key.equalsIgnoreCase("pullDownAction")) {//不执行太多逻辑
                    String jsCode = tableStyle.getString(key);
                    //        mRecyclerView.setRefreshAction();
                } else if (key.equalsIgnoreCase("pullUpAction")) {
                    String jsCode = tableStyle.getString(key);
                    //        mRecyclerView.setLoadMoreAction();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//            recyclerView.post(new Runnable() {
//        @Override
//        public void run() {
////                recyclerView.showSwipeRefresh();
////                getData(true);
//        }
//    });

//    public void getData(final boolean isRefresh, final MyRecyclerView mRecyclerView, final RecyclerAdapter<Bean> mAdapter) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isRefresh) {
////                    page = 1;
//                    mAdapter.clear();
//                    mAdapter.addAll(getVirtualData());
//                    mRecyclerView.dismissSwipeRefresh();
//                    mRecyclerView.getRecyclerView().scrollToPosition(0);
//                } else {
//                    mAdapter.addAll(getVirtualData());
////                    if (page >= 3) {
////                        mRecyclerView.showNoMore();
////                    }
//                }
//            }
//        }, 1500);
//    }

}
