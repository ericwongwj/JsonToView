package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.bean.ContactData;
import com.example.tn_ma_l30000048.myjsontest.model.HeaderInfo;
import com.example.tn_ma_l30000048.myjsontest.model.RequestInfo;
import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/7/28.
 * 这里对应的是一个json文件  只有一个rootNode也就是说只有一个根部局
 */

public class JsonRoot {
    static final String TAG = "JsonRoot ";

    public String SDKVersion;
    public String ModuleVersion;
    public List<String> mJSContext;
    public int containerType;//0 view 1 占满整个屏幕 为viewcontroller
    public double containerHeight;//dp
    public double containerWidth;
    public String registerProperty;

    private Context mContext;
    private ViewWrapper rootViewWrapper;//根布局只会有一个rootNode
    private Map<String, Object> mDataMap;
    Runnable contactListRunnable = new Runnable() {
        @Override
        public void run() {
            addListData();
        }
    };
    private HeaderInfo mHeaderInfo;
    private List<RequestInfo> mRequestInfoList;
    private Handler mHandler = new Handler();

    public JsonRoot(JSONObject jsonObject, Context context, int parentWidth, int parentHeight) {
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
                System.out.println(TAG + "headerInfo:" + mHeaderInfo.toString());
            }
            if (jsonObject.has("requestInfo")) {
                parseRequestInfo(jsonObject.getJSONArray("requestInfo"));
            }

            //渲染界面  主要问题在于 如果只是一个控件
            JSONObject rootNode = jsonObject.getJSONObject("rootNode");
            ViewGroupWrapper virtualRoot = new ViewGroupWrapper(context);
            if (rootNode.getInt("nodeType") == 4 || rootNode.getInt("nodeType") == 0) {
                rootViewWrapper = ViewGroupFactory.build(virtualRoot, rootNode, parentWidth, (int) containerHeight);
                rootViewWrapper.setDataMap(mDataMap);
            } else {
                rootViewWrapper = ViewFactory.build(rootNode, virtualRoot, parentWidth, (int) containerHeight);
                rootViewWrapper.setDataMap(mDataMap);
            }

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
    //得到请求路径就直接请求网络？  编码阶段先添加假数据  如何考虑
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
            mDataMap = new HashMap<>();
            //TODO: 去请求界面（列表的第一个？）
            mHandler.postDelayed(contactListRunnable, 1500);
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

    private void setData() {//遍历view树来set数据

    }

    //    ========test fake data============
    void addListData() {
        List<ContactData> contactData = new ArrayList<>();
        int[] picIds = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};
        for (int i = -0; i < 20; i++) {
            ContactData data = new ContactData("联系人" + i, picIds[i % 3]);
            contactData.add(data);
        }
        mDataMap.put("recentContactList", contactData);
        System.out.println(mDataMap.get("recentContactList").getClass().getSimpleName());
    }

    public ViewWrapper getRootViewWrapper() {
        return rootViewWrapper;
    }

    public View getJsonView() {
        return rootViewWrapper.getJsonView();
    }

    public HeaderInfo getHeaderInfo() {
        return mHeaderInfo;
    }

    public List<RequestInfo> getRequestInfo() {
        return mRequestInfoList;
    }


}
