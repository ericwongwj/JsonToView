package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.view.Bean;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;
import com.example.tn_ma_l30000048.myjsontest.view.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonListView {

    static RecyclerAdapter<Bean> madapter;

    public static MyRecyclerView build(JSONObject body, Context context, int parentWidth, int parentHeight) {
        System.out.println("----build MyRecyclerView----");
        final MyRecyclerView recyclerView = new MyRecyclerView(context);
        JsonBasicWidget.setBasic(body, recyclerView);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),recyclerView,parentWidth, parentHeight);

        RecyclerAdapter<Bean> adapter = new RecyclerAdapter<Bean>(context);
        adapter.setHeader(JsonHelper.readLocalJson(context, "TNChatContactHead.json"));
        adapter.setFooter(JsonHelper.readLocalJson(context, "TNChatContactFoot.json"));
        adapter.setCell(JsonHelper.readLocalJson(context, "TNChatContactCell.json"));
        adapter.addAll(getVirtualData());

        recyclerView.setAdapter(adapter);
        setListStyle(JsonHelper.getStyles(body),recyclerView);

        recyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        recyclerView.post(new Runnable() {
            @Override
            public void run() {
//                recyclerView.showSwipeRefresh();
//                getData(true);
            }
        });

        return recyclerView;
    }


    static void setListStyle(JSONObject json, MyRecyclerView rv) {
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
                }else if(key.equalsIgnoreCase("tableStyle")){
                    JSONObject tableStyle=json.getJSONObject(key);
                    setTableStyle(tableStyle, rv);
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    static void setTableStyle(JSONObject tableStyle, MyRecyclerView rv) {
        Iterator<String> keys=tableStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {
                    String cell = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(cell))
                        setCell(cell);
                } else if (key.equalsIgnoreCase("headView")) {
                    String headView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(headView))
                        setHeader(headView);
                } else if (key.equalsIgnoreCase("footView")) {
                    String footView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(footView))
                        setFooter(footView);
                } else if (key.equalsIgnoreCase("dataPath")) {
                    JSONArray data = tableStyle.getJSONArray(key);
//                    setData();
                }
                else if(key.equalsIgnoreCase("enablePullUpToLoadMore")){
                    if (tableStyle.getInt(key) == 0)
                        rv.setLoadMoreAble(false);
                    else rv.setLoadMoreAble(true);
                }
                else if(key.equalsIgnoreCase("enablePullDownToRefresh")){
                    if (tableStyle.getInt(key) == 0)
                        rv.getSwipeRefreshLayout().setEnabled(false);
                    else rv.getSwipeRefreshLayout().setEnabled(true);
                }
                else if(key.equalsIgnoreCase("pullDownAction")){
                    String jsCode = tableStyle.getString(key);
                    //        mRecyclerView.setRefreshAction();
                }
                else if(key.equalsIgnoreCase("pullUpAction")){
                    String jsCode = tableStyle.getString(key);
                    //        mRecyclerView.setLoadMoreAction();
                }
                else if(key.equalsIgnoreCase("insertViews")){
                    JSONArray inserViews=tableStyle.getJSONArray(key);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void setHeader(String name) {
    }

    static void setFooter(String name) {

    }

    static void setCell(String name) {

    }

    public static Bean[] getVirtualData() {
        return new Bean[]{
                new Bean("Demo", R.drawable.pic1),
                new Bean("Demo", R.drawable.pic2),
                new Bean("Demo", R.drawable.pic3),
                new Bean("Demo", R.drawable.pic1),
                new Bean("Demo", R.drawable.pic2),
                new Bean("Demo", R.drawable.pic3),
                new Bean("Demo", R.drawable.pic1),
                new Bean("Demo", R.drawable.pic2),
        };
    }

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
