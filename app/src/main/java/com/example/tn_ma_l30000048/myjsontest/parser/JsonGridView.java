package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.tn_ma_l30000048.myjsontest.model.AbsoluteCalculate;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;
import com.example.tn_ma_l30000048.myjsontest.view.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/8/1.
 */

public class JsonGridView {
    public static MyRecyclerView build(JSONObject body, Context context, int parentWidth, int parentHeight) {
//        GridLayout gridLayout = new GridLayout(context);
//        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), gridLayout, parentWidth, parentHeight);

        MyRecyclerView gridView = new MyRecyclerView(context);
        gridView.setLayoutManager(new GridLayoutManager(context, 2));
//        return gridLayout;
        return gridView;
    }


    static void setBaseStyle(JSONObject json, MyRecyclerView rv, RecyclerAdapter adapter) {
        Iterator<String> keys = json.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("backgroundColor")) {
                    String bgColor = json.getString(key);
                    rv.setBackgroundColor(JasonHelper.parseColor(bgColor));
                } else if (key.equalsIgnoreCase("collectionStyle")) {
                    JSONObject tableStyle = json.getJSONObject(key);
                    setGridStyle(tableStyle, rv, adapter);
                }
            }
        } catch (Exception e) {
            Log.e(e.getClass().getSimpleName(), e.getMessage());
        }
    }

    static void setGridStyle(JSONObject gridStyle, MyRecyclerView rv, RecyclerAdapter adapter) {
        Iterator<String> keys = gridStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {
                    String cell = gridStyle.getString(key);
                    if (!TextUtils.isEmpty(cell)) {//当创建cell模板时，需要设置containerHeight用于返回相应cell的高度??
                        adapter.setCell(JsonHelper.findLocalJsonByName(rv.getContext(), cell));
                    }
                } else if (key.equalsIgnoreCase("scrollDirection")) {
                    int direction = gridStyle.getInt(key);

                } else if (key.equalsIgnoreCase("dataPath")) {
                    JSONArray data = gridStyle.getJSONArray(key);
//                    setCellData();
                } else if (key.equalsIgnoreCase("enablePullUpToLoadMore")) {
                    if (gridStyle.getInt(key) == 0)
                        rv.setLoadMoreAble(false);
                    else rv.setLoadMoreAble(true);
                } else if (key.equalsIgnoreCase("enablePullDownToRefresh")) {
                    if (gridStyle.getInt(key) == 0)
                        rv.getSwipeRefreshLayout().setEnabled(false);
                    else rv.getSwipeRefreshLayout().setEnabled(true);
                } else if (key.equalsIgnoreCase("pullDownAction")) {//不执行太多逻辑
                    String jsCode = gridStyle.getString(key);
                    //        mRecyclerView.setRefreshAction();
                } else if (key.equalsIgnoreCase("pullUpAction")) {
                    String jsCode = gridStyle.getString(key);
                    //        mRecyclerView.setLoadMoreAction();
                } else if (key.equalsIgnoreCase("itemSize")) {
                    JSONObject itemSize = gridStyle.getJSONObject(key);
                    AbsoluteCalculate width = JsonUtils.decode(itemSize.toString(), AbsoluteCalculate.class);
                    AbsoluteCalculate height = JsonUtils.decode(itemSize.toString(), AbsoluteCalculate.class);

                } else if (key.equalsIgnoreCase("minimumInteritemSpacing")) {

                } else if (key.equalsIgnoreCase("minimumLineSpacing")) {

                } else if (key.equalsIgnoreCase("scrollDisabled")) {
                    int scrollDisabled = gridStyle.getInt(key);
                    rv.getRecyclerView().setNestedScrollingEnabled(scrollDisabled == 0);
                } else if (key.equalsIgnoreCase("requestId")) {
                    int reqId = gridStyle.getInt(key);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
