package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
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

public class JsonRoot extends ViewGroupWrapper {
    static final String TAG = "JsonRoot ";

    public String SDKVersion;
    public String ModuleVersion;
    public List<String> mJSContext;
    public int containerType;//0 view 1 占满整个屏幕 为viewcontroller
    public double containerHeight;//dp
    public double containerWidth;
    public String registerProperty;

    private Context mContext;
    private Map<String, Object> mDataMap;
    private HeaderInfo mHeaderInfo;
    private List<RequestInfo> mRequestInfoList;
    private Handler mHandler = new Handler();

    public JsonRoot(JSONObject jsonObject, final Context context, int parentWidth, int parentHeight) {
        super(context);
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

            mDataMap = new HashMap<>();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    constructDataMap(mDataMap, JsonHelper.readLocalDataJson(context, "testData.json"));
                    setDataToView();
                }
            }, 300);

            //渲染界面  主要问题在于 如果只是一个控件
            JSONObject rootNode = jsonObject.getJSONObject("rootNode");
            if (rootNode.getInt("nodeType") == 4 || rootNode.getInt("nodeType") == 0) {
                ViewWrapper vw = ViewGroupFactory.build(rootNode, this, (int) containerWidth, (int) containerHeight);
                mJsonView = vw.getJsonView();
            } else {
                //need to test and modify
                ViewWrapper vw = ViewFactory.build(rootNode, this, (int) containerWidth, (int) containerHeight);
                mJsonView = vw.getJsonView();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsonRoot(JSONObject jsonObject, final Context context, int parentWidth, int parentHeight, boolean isCell) {
        super(context);
        System.out.println("JSON CELL ROOT ");
        mContext = context;
        try {
            SDKVersion = jsonObject.getString("SDKVersion");
            ModuleVersion = jsonObject.getString("ModuleVersion");
            if (jsonObject.has("registerProperty"))
                registerProperty = jsonObject.getString("registerProperty");//js code
            if (jsonObject.has("JSContext"))
                parseJSContext(jsonObject.getJSONArray("JSContext"));
            if (jsonObject.has("containerType"))//0:view  1:page
                containerType = jsonObject.getInt("containerType");

            parseWandH(jsonObject, parentWidth, parentHeight);

            if (jsonObject.has("requestInfo")) {
                parseRequestInfo(jsonObject.getJSONArray("requestInfo"));
            }

            JSONObject rootNode = jsonObject.getJSONObject("rootNode");
            if (rootNode.getInt("nodeType") == 4 || rootNode.getInt("nodeType") == 0) {
                ViewWrapper vw = ViewGroupFactory.build(rootNode, this, (int) containerWidth, (int) containerHeight);
                mJsonView = vw.getJsonView();
            } else {
                //need to test and modify
                ViewWrapper vw = ViewFactory.build(rootNode, this, (int) containerWidth, (int) containerHeight);
                mJsonView = vw.getJsonView();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //对请求到的数据到model需要自己封装
    public static void constructDataMap(Map<String, Object> map, JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);
                if (value instanceof JSONArray) {
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (int i = 0; i < ((JSONArray) value).length(); i++) {
                        JSONObject listObj = ((JSONArray) value).getJSONObject(i);
                        Map<String, Object> subMap = new HashMap<>();
                        constructDataMap(subMap, listObj);
                        list.add(subMap);
                    }
                    map.put(key, list);
                } else if (value instanceof JSONObject) {
                    Map<String, Object> subMap = new HashMap<>();
                    constructDataMap(subMap, jsonObject);
                    map.put(key, subMap);
                } else {
                    map.put(key, jsonObject.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Object getDataFromMap(Map<String, Object> dataMap, List<String> keys) {
        int i = 0;
        while (i < keys.size() - 1) {
            dataMap = (Map<String, Object>) dataMap.get(keys.get(i++));
        }
        return dataMap.get(keys.get(i));
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
    //得到请求路径就直接请求网络？  编码阶段先添加假数据  如何考虑  要注意一些固定数据的加载和网络数据请求的关系
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
//            System.out.println(TAG + "container height:" + height + " dp");
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
                System.out.println("containerWidth is js code");
            else {
                containerHeight = Double.parseDouble(width);
                containerHeight = DensityUtils.dp2px(mContext, (float) containerHeight);
            }
        } else containerWidth = parentWidth;
    }

    public void setDataToView() {//遍历view树来set数据
        if (mDataMap == null) {
            System.out.println(TAG + " mdatamap");
            return;//这里的逻辑不完善
        }
        for (ViewWrapper vw : mSubViewWrappers) {
            if (vw.getDataSource() != null) {
                AtomicData dataSource = vw.getDataSource();
                if (vw.getJsonView() instanceof TextView) {
                    TextView tv = ((TextView) vw.getJsonView());
                    System.out.println(TAG + "x:" + tv.getX() + " y:" + tv.getY() + " w:" + tv.getWidth() + "h" + tv.getHeight() + " " + tv.isShown() + " " + tv.getText());
                    if (dataSource.getDataType() == 1) {//另外的情况直接加载text了
                        List<String> keys = dataSource.getDataPaths();
                        Object o = getDataFromMap(mDataMap, keys);//这的做的转换尽量安全
                        tv.setText(o.toString());

                    }
                } else if (vw.getJsonView() instanceof ImageView) {
                    if (dataSource.getDataType() == 0) {
                        //TODO:如何读取本地的图片？
                        ((ImageView) vw.getJsonView()).setImageResource(R.drawable.icon);
                    } else if (dataSource.getDataType() == 1) {
                        List<String> keys = dataSource.getDataPaths();
                        String url = (String) getDataFromMap(mDataMap, keys);
                        Glide.with(mContext).load(url).asBitmap().into((ImageView) vw.getJsonView());
                    }
                } else if (vw instanceof CollectionViewWrapper) {//对于没有集合控件的来说 只需要在root中遍历一次setview即可 可是对于列表 head那些的数据可能在root的data当中
                    if (vw.getDataSource().getDataType() == 1) {
                        List<String> keys = dataSource.getDataPaths();
                        List<Map<String, Object>> listData = (List<Map<String, Object>>) getDataFromMap(mDataMap, keys);//每一个item的数据也是一个map
                        CollectionViewWrapper cvw = ((CollectionViewWrapper) vw);
                        cvw.setRootDataMap(mDataMap);
                        cvw.initRecyclerView(listData);//只负责第一次的加载 后续刷新在控件内实现 那样的话 数据还会加载到rootmap当中吗
                    }
                }
            }
        }
    }


    public HeaderInfo getHeaderInfo() {
        return mHeaderInfo;
    }


    public List<RequestInfo> getRequestInfo() {
        return mRequestInfoList;
    }


}
