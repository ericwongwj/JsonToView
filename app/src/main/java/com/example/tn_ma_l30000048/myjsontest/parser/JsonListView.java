package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.view.Bean;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonListView {

    public static MyRecyclerView build(JSONObject body, Context context, int parentWidth, int parentHeight) {
        MyRecyclerView recyclerView = new MyRecyclerView(context);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),recyclerView,parentWidth, parentHeight);
        setListStyle(JsonHelper.getStyles(body),recyclerView);
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
                    setTableStyle(tableStyle, rv.getRecyclerView());
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    static void setTableStyle(JSONObject tableStyle, RecyclerView rv){
        Iterator<String> keys=tableStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {
                    String cell = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(cell))
                        setCell(cell);
                } else if (key.equalsIgnoreCase("dataPath")) {
                    JSONArray data = tableStyle.getJSONArray(key);
                    setData();
                }
                else if(key.equalsIgnoreCase("enablePullUpToLoadMore")){
                    int isEnabled=tableStyle.getInt(key);
                }
                else if(key.equalsIgnoreCase("enablePullDownToRefresh")){
                    int isEnabled=tableStyle.getInt(key);
                }
                else if(key.equalsIgnoreCase("pullDownAction")){
                    String jsCode = tableStyle.getString(key);
                }
                else if(key.equalsIgnoreCase("pullUpAction")){
                    String jsCode = tableStyle.getString(key);
                }
                else if(key.equalsIgnoreCase("headView")){
                    String headView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(headView))
                        setHeader(headView);
                }
                else if(key.equalsIgnoreCase("footView")){
                    String footView = tableStyle.getString(key);
                    if (!TextUtils.isEmpty(footView))
                        setFooter(footView);
                }
                else if(key.equalsIgnoreCase("insertViews")){
                    JSONArray inserViews=tableStyle.getJSONArray(key);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void setData() {

    }

    static void setHeader(String name) {

    }

    static void setFooter(String name) {

    }

    static void setCell(String name) {
//        ViewGroupFactory.build()
    }

    public Bean[] getVirtualData() {
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
}
