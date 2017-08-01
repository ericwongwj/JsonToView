package com.example.tn_ma_l30000048.myjsontest;

import android.content.Context;
import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.parser.ViewFactory;
import com.example.tn_ma_l30000048.myjsontest.parser.ViewGroupFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/28.
 * 这里对应的只能是一个json文件
 */

public class MyRoot {
    public String SDKVersion;
    public String ModuleVersion;
    public int containerHeight;
    public String registerProperty;
    public View myJsonView;

    public MyRoot(JSONObject jsonObject, Context context, int parentWith, int parentHeight){
        try {
            SDKVersion=jsonObject.getString("SDKVersion");
            ModuleVersion=jsonObject.getString("ModuleVersion");
            JSONObject rootNode=jsonObject.getJSONObject("rootNode");
            System.out.println("my root " + parentWith + " " + parentHeight);
            if(rootNode.getInt("nodeType")==4)
                myJsonView= ViewGroupFactory.build(rootNode,context,parentWith, parentHeight);
            else
                myJsonView=ViewFactory.build(rootNode,context,parentWith,parentHeight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public View getJsonView(){
         return myJsonView;
    }
}
