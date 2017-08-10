package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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

    //问题：1.各个cell的模板请求 2.数据的加载 3.insertView
    //网络请求成功后再setAdapter
    public static MyRecyclerView buildTableView(JSONObject body, CollectionViewWrapper viewWrapper, int parentWidth, int parentHeight) {
        System.out.println("----buildViewGroup MyRecyclerView----");
        MyRecyclerView recyclerView = new MyRecyclerView(viewWrapper.getContext());
        JsonViewUtils.setTagToWrapper(body, recyclerView, new ViewWrapper());
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), recyclerView, parentWidth, parentHeight);
        setBaseStyle(JsonHelper.getStyles(body), recyclerView, viewWrapper);

        recyclerView.setLayoutManager(new LinearLayoutManager(viewWrapper.getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        recyclerView.showSwipeRefresh();//在加载数据之后dismiss

        return recyclerView;
    }

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
        Context context = rv.getContext();
        Iterator<String> keys=tableStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {//这里有本地/网络IO
                    String cellName = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(cellName)) {//当创建cell模板时，需要设置containerHeight用于返回相应cell的高度??
                        viewWrapper.setCellJson(JsonHelper.findLocalJsonByName(context, cellName));
                    }
                } else if (key.equalsIgnoreCase("headView")) {
                    String headName = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(headName)) {
                        viewWrapper.setHeadJson(JsonHelper.findLocalJsonByName(context, headName));
                    }
                } else if (key.equalsIgnoreCase("footView")) {
                    String footName = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(footName)) {
                        viewWrapper.setFootJson(JsonHelper.findLocalJsonByName(context, footName));
                    }
                } else if (key.equalsIgnoreCase("insertViews")) {//改变的时候 notifydatasetchange
                    JSONArray insertInfos = tableStyle.getJSONArray(key);
                    SparseArray<JSONObject> insertViews = new SparseArray<>();
                    for (int i = 0; i < insertInfos.length(); i++) {
                        JSONObject insertInfo = insertInfos.getJSONObject(i);
                        String name = insertInfo.getString("templateName");
                        int row = insertInfo.getInt("row");
                        JSONObject insertUI = JsonHelper.findLocalJsonByName(context, name);
                        insertViews.put(row, insertUI);
                    }
                    viewWrapper.setInsertViewJson(insertViews);
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

}
