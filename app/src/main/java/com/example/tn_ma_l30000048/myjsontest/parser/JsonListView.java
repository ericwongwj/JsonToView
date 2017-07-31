package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonListView {

    public static RecyclerView build(JSONObject body, Context context, int parentWidth, int parentHeight){
        RecyclerView recyclerView=new RecyclerView(context);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),recyclerView,parentWidth, parentHeight);
        setListStyle(JsonHelper.getStyles(body),recyclerView);
        return recyclerView;
    }

    static void setListStyle(JSONObject json, RecyclerView rv){
        Iterator<String> keys=json.keys();
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("backgroundColor")){
                    int bgColor=json.getInt(key);
                    rv.setBackgroundColor(bgColor);
                }else if(key.equalsIgnoreCase("scrollDisabled")) {
                    int scrollDisabled = json.getInt(key);
                    rv.setNestedScrollingEnabled(scrollDisabled==0);
                }else if(key.equalsIgnoreCase("tableStyle")){
                    JSONObject tableStyle=json.getJSONObject(key);
                    setTableStyle(tableStyle,rv);
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
                    String jsCode = tableStyle.getString(key);
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
                }
                else if(key.equalsIgnoreCase("footView")){
                    String footView = tableStyle.getString(key);
                }
                else if(key.equalsIgnoreCase("insertViews")){
                    JSONArray inserViews=tableStyle.getJSONArray(key);
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }
    }
}
