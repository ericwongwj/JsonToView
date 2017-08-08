package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.example.tn_ma_l30000048.myjsontest.R;
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

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        MyRecyclerView myRecyclerView = buildTableView(body, parent.getContext(), parentWidth, parentHeight);
        return new ViewWrapper(myRecyclerView);
    }

    public static MyRecyclerView buildTableView(JSONObject body, Context context, int parentWidth, int parentHeight) {
        System.out.println("----buildViewGroup MyRecyclerView----");
        final MyRecyclerView recyclerView = new MyRecyclerView(context);
        JsonViewUtils.setBasic(body, recyclerView, new ViewWrapper());
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), recyclerView, parentWidth, parentHeight);

        RecyclerAdapter<Bean> adapter = new RecyclerAdapter<Bean>(context);
//        adapter.setHeader(JsonHelper.readLocalJson(context, "TNChatContactHead.json"));
//        adapter.setFooter(JsonHelper.readLocalJson(context, "TNChatContactFoot.json"));
//        adapter.setCell(JsonHelper.readLocalJson(context, "TNChatContactCell.json"));
        adapter.addAll(getVirtualData());

        recyclerView.setAdapter(adapter);
        setBaseStyle(JsonHelper.getStyles(body), recyclerView, adapter);

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


    static void setBaseStyle(JSONObject json, MyRecyclerView rv, RecyclerAdapter<Bean> adapter) {
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
                    setTableStyle(tableStyle, rv, adapter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void setTableStyle(JSONObject tableStyle, MyRecyclerView rv, RecyclerAdapter<Bean> adapter) {
        Iterator<String> keys=tableStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {//这里有本地/网络IO
                    String cell = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(cell)) {//当创建cell模板时，需要设置containerHeight用于返回相应cell的高度??
                        adapter.setCell(JsonHelper.findLocalJsonByName(rv.getContext(), cell));
                    }
                } else if (key.equalsIgnoreCase("headView")) {
                    String headView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(headView)) {
                        JSONObject headJson = JsonHelper.findLocalJsonByName(rv.getContext(), headView);//这一步耗时
                        adapter.setHeader(headJson);
                    }
                } else if (key.equalsIgnoreCase("footView")) {
                    String footView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(footView)) {
                        JSONObject footJson = JsonHelper.findLocalJsonByName(rv.getContext(), footView);//耗时
                        adapter.setFooter(footJson);
                    }
                } else if (key.equalsIgnoreCase("insertViews")) {//改变的时候 notifydatasetchange
                    JSONArray insertViews = tableStyle.getJSONArray(key);
                    List<JSONObject> inserViews = new ArrayList<>();
                    for (int i = 0; i < insertViews.length(); i++) {
                        JSONObject insertView = insertViews.getJSONObject(i);
                        String templateName = insertView.getString("templateName");
                    }
                    adapter.setInsertViews(inserViews);
                } else if (key.equalsIgnoreCase("dataPath")) {
                    JSONArray data = tableStyle.getJSONArray(key);
                    for (int i = 0; i < data.length(); i++)
                        System.out.println("dataPath" + i + " " + data.getString(i));

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
                } else if (key.equalsIgnoreCase("pullDownAction")) {//不执行太多逻辑
                    String jsCode = tableStyle.getString(key);
                    //        mRecyclerView.setRefreshAction();
                }
                else if(key.equalsIgnoreCase("pullUpAction")){
                    String jsCode = tableStyle.getString(key);
                    //        mRecyclerView.setLoadMoreAction();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
