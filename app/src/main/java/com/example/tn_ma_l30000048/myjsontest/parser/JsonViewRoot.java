package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.tn_ma_l30000048.myjsontest.model.HeaderInfo;
import com.example.tn_ma_l30000048.myjsontest.model.RequestInfo;
import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by tn-ma-l30000048 on 17/7/28.
 * 这里对应的只能是一个json文件
 */

public class JsonViewRoot {
    static final String TAG = "JsonViewRoot ";

    public String SDKVersion;
    public String ModuleVersion;
    public List<String> mJSContext;
    public int containerType;//0 view 1 占满整个屏幕 为viewcontroller
    public double containerHeight;//dp
    public double containerWidth;
    public String registerProperty;
    public HashMap<String, View> mViewMap = new HashMap<>();//nodeName到子view的map
    public HashMap<String, Object> mDataMap = new HashMap<>();//request得到的所有数据
    private View myJsonView;
    private Context mContext;

    private HeaderInfo mHeaderInfo;
    private List<RequestInfo> mRequestInfoList;


    public JsonViewRoot(JSONObject jsonObject, Context context, int parentWidth, int parentHeight) {
        System.out.println("JSON VIEW ROOT " + parentWidth + " " + parentHeight);
        mContext = context;
        try {
            SDKVersion=jsonObject.getString("SDKVersion");
            ModuleVersion=jsonObject.getString("ModuleVersion");
            if (jsonObject.has("registerProperty"))
                registerProperty = jsonObject.getString("registerProperty");//js code
            if (jsonObject.has("JSContext"))
                parseJSContext(jsonObject.getJSONArray("JSContext"));
            if (jsonObject.has("containerType"))//0:view  1:page
                containerType = jsonObject.getInt("containerType");

            parseWandH(jsonObject, parentWidth, parentHeight);

            if (jsonObject.has("headerInfo")) {
                parseHeaderInfo(jsonObject.getJSONObject("headerInfo"));
                System.out.println(TAG + mHeaderInfo.toString());
            }
            if (jsonObject.has("requestInfo")) {
                parseRequestInfo(jsonObject.getJSONArray("requestInfo"));
                System.out.println(TAG + mRequestInfoList.get(0).toString());
            }

            JSONObject rootNode = jsonObject.getJSONObject("rootNode");
            if (rootNode.getInt("nodeType") == 4)
                myJsonView = ViewGroupFactory.build(rootNode, context, parentWidth, (int) containerHeight);
            else if (rootNode.getInt("nodeType") == 0)
                myJsonView = ViewGroupFactory.build(rootNode, context, parentWidth, (int) containerHeight);
            else
                myJsonView = ViewFactory.build(rootNode, context, parentWidth, (int) containerHeight);
            initViewMap();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseHeaderInfo(JSONObject jsonObject) throws JSONException {
        mHeaderInfo = new HeaderInfo();
        if (jsonObject.has("hasBackButton")) {
            mHeaderInfo.hashBackButton = jsonObject.getInt("hasBackButton");
        }
        if (jsonObject.has("headTitle")) {
            mHeaderInfo.headTitle = jsonObject.getString("headTitle");
        }
        if (jsonObject.has("extensionButtons")) {
            mHeaderInfo.extensionButtons = new ArrayList<>();
            JSONArray extBtns = jsonObject.getJSONArray("extensionButtons");
            if (extBtns != null && extBtns.length() != 0) {
                for (int i = 0; i < extBtns.length(); i++) {
                    JSONObject jsonBtn = extBtns.getJSONObject(i);
                    HeaderInfo.ExtensionButton btn = new HeaderInfo.ExtensionButton();
                    btn.setAction(jsonBtn.getString("action"));
                    btn.setText(jsonBtn.getString("text"));
                    btn.setImageSource(jsonBtn.getString("imageResource"));
                    mHeaderInfo.extensionButtons.add(btn);
                }
            }
        }
    }

    //array，特殊场景太多，所以单一请求无法满足需求。当前所有请求处理并行发送；
    //下拉刷新会将重新发送所有请求；上拉加载只会发送第一个请求；
    private void parseRequestInfo(JSONArray jsonArray) throws JSONException {
        mRequestInfoList = new ArrayList<>();
        if (!jsonArray.isNull(0)) {
            int index = 0;
            while (!jsonArray.isNull(index)) {
                RequestInfo requestInfo = new RequestInfo();
                JSONObject object = jsonArray.getJSONObject(index);
                if (object.has("baseUrl")) {
                    requestInfo.baseUrl = object.getString("baseUrl");
                }
                if (object.has("path")) {
                    requestInfo.path = object.getString("path");
                }
                if (object.has("parameters")) {//dic的值可以是一个string，也可以是一个js code————"{{/*js code*/}}"
                    Object obj = object.get("parameters");
                    if (obj instanceof String) {
                        requestInfo.parameterString = (String) obj;
                    } else if (obj instanceof JSONObject) {
                        requestInfo.parameters = new HashMap<>();
                        JSONObject paraJson = (JSONObject) obj;
                        for (Iterator<String> it = paraJson.keys(); it.hasNext(); ) {
                            String key = it.next();
                            requestInfo.parameters.put(key, paraJson.getString(key));
                        }
                    }
                }
                index++;
                mRequestInfoList.add(requestInfo);
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
            String height = jsonObject.getString("containerHeight");//in dp
            System.out.println(TAG + "container height:" + height + " dp");
            if (height.substring(0, 2).equals("{{"))
                System.out.println(TAG + "containerHeight is js code");
            else {
                containerHeight = Double.parseDouble(height);
                containerHeight = DensityUtils.dp2px(mContext, (float) containerHeight);
            }
        } else containerHeight = parentHeight;


        if (jsonObject.has("containerWidth")) {
            System.out.println("has container width");
            String width = jsonObject.getString("containerWidth");//in dp
            if (width.substring(0, 2).equals("{{"))
                System.out.println("containerHeight is js code");
            else {
                containerHeight = Double.parseDouble(width);
                containerHeight = DensityUtils.dp2px(mContext, (float) containerHeight);
            }
        } else containerWidth = parentWidth;
    }

    //recyclerview的处理？三个类型的cell？
    public void initViewMap() {
        Queue<View> queue = new LinkedList<>();
        queue.offer(myJsonView);
        System.out.println("initViewMap");
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

    //需要自己根据nodeName返回相应的view 并且调用者应该知道view的具体类型
    public View findViewByNodeName(String nodeName) {
        return findViewByNodeName(nodeName, null);
    }

    public View findViewByNodeName(String nodeName, Class<? extends View> viewClazz) {
        if (viewClazz == null)
            return mViewMap.get(nodeName);
        return viewClazz.cast(mViewMap.get(nodeName));
    }

    public View getJsonView() {
        return myJsonView;
    }

    public HeaderInfo getHeaderInfo() {
        return mHeaderInfo;
    }

    public List<RequestInfo> getRequestInfo() {
        return mRequestInfoList;
    }
}
