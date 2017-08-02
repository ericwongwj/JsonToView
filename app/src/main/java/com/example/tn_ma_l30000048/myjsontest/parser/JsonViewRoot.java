package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/28.
 * 这里对应的只能是一个json文件
 */

public class JsonViewRoot {
    static final String TAG = "JsonViewRoot";

    public String SDKVersion;
    public String ModuleVersion;
    public int containerHeight;//dp
    public String registerProperty;
    public View myJsonView;
    Context context;


    public JsonViewRoot(JSONObject jsonObject, Context context, int parentWith, int parentHeight) {
        try {
            this.context = context;
            SDKVersion=jsonObject.getString("SDKVersion");
            ModuleVersion=jsonObject.getString("ModuleVersion");
            if (jsonObject.has("registerProperty"))
                registerProperty = jsonObject.getString("registerProperty");//js code
            if (jsonObject.has("containerHeight")) {
                String height = jsonObject.getString("containerHeight");//in dp
                if (height.substring(0, 2).equals("{{"))
                    System.out.println("containerHeight is js code");
                else
                    containerHeight = Integer.parseInt(height);
            } else containerHeight = parentHeight;
            containerHeight = (int) DensityUtils.dp2px(context, containerHeight);
            JSONObject rootNode=jsonObject.getJSONObject("rootNode");
            System.out.println("my root " + parentWith + " " + parentHeight);
            if(rootNode.getInt("nodeType")==4)
                myJsonView = ViewGroupFactory.build(rootNode, context, parentWith, containerHeight);
            else
                myJsonView = ViewFactory.build(rootNode, context, parentWith, containerHeight);
            showViewTree();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public View getJsonView(){
         return myJsonView;
    }

    public void showViewTree() {
        System.out.println(TAG + ":" + myJsonView.getTag());
        if (myJsonView instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) myJsonView).getChildCount(); i++) {
                View subView = ((ViewGroup) myJsonView).getChildAt(i);
                System.out.println("SubView:" + subView.getTag());
            }
        }
    }

    //需要自己根据nodeName返回相应的view 并且调用者应该知道view的具体类型
    public View findViewByNodeName(String nodeName) {
        View view = new View(context);
        return view;
    }
}
