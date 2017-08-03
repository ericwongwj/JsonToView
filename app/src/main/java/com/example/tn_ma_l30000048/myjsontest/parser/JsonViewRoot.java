package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by tn-ma-l30000048 on 17/7/28.
 * 这里对应的只能是一个json文件
 */

public class JsonViewRoot {
    static final String TAG = "JsonViewRoot";

    public String SDKVersion;
    public String ModuleVersion;
    public List<String> mJSContext;
    public int containerType;//0 view 1 占满整个屏幕 为viewcontroller
    public int containerHeight;//dp
    public int containerWidth;
    public String registerProperty;
    public HashMap<String, View> mViewMap = new HashMap<>();
    private View myJsonView;
    private List<JsonViewRoot> jsonViewRootList = new ArrayList<>();
    private Context mContext;


    public JsonViewRoot(JSONObject jsonObject, Context context, int parentWidth, int parentHeight) {
        System.out.println("my root " + parentWidth + " " + parentHeight);
        mContext = context;
        try {
            SDKVersion=jsonObject.getString("SDKVersion");
            ModuleVersion=jsonObject.getString("ModuleVersion");
            if (jsonObject.has("registerProperty"))
                registerProperty = jsonObject.getString("registerProperty");//js code
            if (jsonObject.has("JSContext"))
                parseJSContext(jsonObject.getJSONArray("JSContext"));
            if (jsonObject.has("containerType"))
                containerType = jsonObject.getInt("containerType");

            parseWandH(jsonObject, parentWidth, parentHeight);

            JSONObject rootNode=jsonObject.getJSONObject("rootNode");
            if(rootNode.getInt("nodeType")==4)
                myJsonView = ViewGroupFactory.build(rootNode, context, parentWidth, containerHeight);
            else if (rootNode.getInt("nodeType") == 0)
                myJsonView = ViewGroupFactory.build(rootNode, context, parentWidth, containerHeight);
            else
                myJsonView = ViewFactory.build(rootNode, context, parentWidth, containerHeight);
            initViewMap();

            if (jsonObject.has("headerInfo")) {
                parseHeaderInfo(jsonObject.getJSONObject("headerInfo"));
            }
            if (jsonObject.has("requestInfo")) {
                parseRequestInfo(jsonObject.getJSONArray("requestInfo"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseHeaderInfo(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("hasBackButton")) {

        }
        if (jsonObject.has("headTitle")) {

        }
    }

    private void parseRequestInfo(JSONArray jsonArray) throws JSONException {
        if (!jsonArray.isNull(0)) {
            int index = 0;
            while (!jsonArray.isNull(index)) {
                System.out.println("requestInfo " + index);
                JSONObject object = jsonArray.getJSONObject(index);
                if (object.has("baseUrl")) {

                }
                if (object.has("path")) {

                }
                if (object.has("parameter")) {

                }
                index++;
            }
        }

    }

    private void parseJSContext(JSONArray jsonArray) throws JSONException {
        mJSContext = new ArrayList<>();
        if (!jsonArray.isNull(0)) {
            int index = 0;
            while (!jsonArray.isNull(index)) {//指定与ui模板相关联的js文件名
                System.out.println("JSContext " + index);
                mJSContext.add(jsonArray.getString(index));
                index++;
            }
        }
    }

    private void parseWandH(JSONObject jsonObject, int parentWidth, int parentHeight) throws JSONException {
        if (jsonObject.has("containerHeight")) {
            System.out.println("has container height");
            String height = jsonObject.getString("containerHeight");//in dp
            if (height.substring(0, 2).equals("{{"))
                System.out.println("containerHeight is js code");
            else {
                containerHeight = Integer.parseInt(height);
                containerHeight = (int) DensityUtils.dp2px(mContext, containerHeight);
            }
        } else containerHeight = parentHeight;


        if (jsonObject.has("containerWidth")) {
            System.out.println("has container width");
            String width = jsonObject.getString("containerWidth");//in dp
            if (width.substring(0, 2).equals("{{"))
                System.out.println("containerHeight is js code");
            else {
                containerHeight = Integer.parseInt(width);
                containerHeight = (int) DensityUtils.dp2px(mContext, containerHeight);
            }
        } else containerWidth = parentWidth;
    }

    //recyclerview的处理？三个类型的cell？
    public void initViewMap() {
        Queue<View> queue = new LinkedList<>();
        queue.offer(myJsonView);
        while (!queue.isEmpty()) {
            View v = queue.poll();
            if (v.getTag() == null)
                continue;
            System.out.println("nodeName:" + v.getTag() + " " + v.getClass().getSimpleName());
            mViewMap.put(v.getTag().toString(), v);
            if (v instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                    View subView = ((ViewGroup) v).getChildAt(i);
                    queue.offer(subView);
                }
            }
        }
    }

    public View getJsonView() {
        return myJsonView;
    }

    //需要自己根据nodeName返回相应的view 并且调用者应该知道view的具体类型
    public View findViewByNodeName(String nodeName) {
        return findViewByNodeName(nodeName, null);
    }

    public View findViewByNodeName(String nodeName, Class<? extends View> viewClazz) {
        if (viewClazz == null)
            return mViewMap.get(nodeName);
        return viewClazz.cast(mViewMap.get(nodeName));
    }

}
