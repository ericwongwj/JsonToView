package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tn_ma_l30000048.myjsontest.model.Layout;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class ViewGroupFactory {
    static final String TAG = ViewGroupFactory.class.getSimpleName() + " ";

    static int currentType;//0:absolute 1:relative

    public static ViewGroupWrapper build(JSONObject body, ViewGroupWrapper parent, Context context) {
        JSONObject layoutJson = JsonHelper.getLayout(body);
        Layout layout = JsonUtils.decode(layoutJson.toString(), Layout.class);
        ViewGroupWrapper vw = null;
        if (parent == null) {//根布局
            if (layout.strategy == 0) {
                currentType = ViewFactory.currentType = 0;
                return buildRootRelativeLayout(body, context);
            } else if (layout.strategy == 1) {
                currentType = ViewFactory.currentType = 1;
                return buildRootRelativeLayout(body, context);
            }
        }
        if (layout.strategy == 0) {
            vw = buildRelativeLayout(body, parent);
            vw.setLayout(layout);
            currentType = ViewFactory.currentType = 0;
        } else if (layout.strategy == 1) {
            vw = buildRelativeLayout(body, parent);
            vw.setLayout(layout);
            currentType = ViewFactory.currentType = 1;
        }
        return vw;
    }

    /**
     * 1.加载layout对象 2.创建wrapper 添加layout 3.设置layout无关属性style和tagId 4.设置子view
     */
    private static ViewGroupWrapper buildRootRelativeLayout(JSONObject body, Context context) {
        System.out.println("----buildViewGroup Root RelativeLayout----");
        JSONObject layoutJson = JsonHelper.getLayout(body);
        Layout layout = JsonUtils.decode(layoutJson.toString(), Layout.class);//这里重复加载一次
        RelativeLayout relativeLayout = new RelativeLayout(context);

        ViewGroupWrapper vgw = new ViewGroupWrapper(relativeLayout);
        vgw.setLayout(layout);//记得设置

        JsonViewUtils.setTagToWrapper(body, relativeLayout, vgw);
        setStyle(JsonHelper.getStyles(body), relativeLayout);

        setSubNode(JsonHelper.getSubNodes(body), vgw);
        return vgw;
    }

    private static ViewGroupWrapper buildRelativeLayout(JSONObject body, ViewGroupWrapper parent) {
        System.out.println("----buildViewGroup FrameLayout----");
        RelativeLayout relativeLayout = new RelativeLayout(parent.getContext());
        JSONObject layoutJson = JsonHelper.getLayout(body);
        Layout layout = JsonUtils.decode(layoutJson.toString(), Layout.class);//这里重复加载一次

        ViewGroupWrapper vgw = new ViewGroupWrapper(relativeLayout);
        vgw.setLayout(layout);

        JsonViewUtils.setTagToWrapper(body, relativeLayout, vgw);
        setStyle(JsonHelper.getStyles(body), relativeLayout);

        setSubNode(JsonHelper.getSubNodes(body), vgw);
        return vgw;
    }

    private static void setStyle(JSONObject json, ViewGroup vg) {
        Iterator<String> keys=json.keys();
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("backgroundColor")){
                    int color = JasonHelper.parseColor(json.getString(key));
                    vg.setBackgroundColor(color);
                }
                //TODO more attributes to add
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    private static void setSubNode(JSONArray nodes, ViewGroupWrapper viewGroupWrapper) {
        try {
            for (int i = 0; i < nodes.length(); i++) {
                System.out.println(TAG + "setSubNode " + i);
                JSONObject nodeBody = nodes.getJSONObject(i);
                ViewWrapper subViewWrapper = ViewFactory.build(nodeBody, viewGroupWrapper);
                //TODO 容易出空指针
                if (subViewWrapper.getJsonView() == null) {
                    throw new NullPointerException("mJsonView is null");
                }
                ((ViewGroup) viewGroupWrapper.getJsonView()).addView(subViewWrapper.getJsonView());
                viewGroupWrapper.appendView(subViewWrapper);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static ViewGroupWrapper buildRootFrameLayout(JSONObject body, Context context) {
        System.out.println("----buildViewGroup Root FrameLayout----");
        JSONObject layoutJson = JsonHelper.getLayout(body);
        Layout layout = JsonUtils.decode(layoutJson.toString(), Layout.class);//这里重复加载一次

        FrameLayout frameLayout = new FrameLayout(context);
        ViewGroupWrapper vgw = new ViewGroupWrapper(frameLayout);
        vgw.setLayout(layout);//记得设置

        JsonViewUtils.setTagToWrapper(body, frameLayout, vgw);
        setStyle(JsonHelper.getStyles(body), frameLayout);

        setSubNode(JsonHelper.getSubNodes(body), vgw);
        return vgw;
    }


    private static ViewGroupWrapper buildFrameLayout(JSONObject body, ViewGroupWrapper parent) {
        System.out.println("----buildViewGroup FrameLayout----");
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        JSONObject layoutJson = JsonHelper.getLayout(body);

        Layout layout = JsonUtils.decode(layoutJson.toString(), Layout.class);//这里重复加载一次
        ViewGroupWrapper vgw = new ViewGroupWrapper(frameLayout);
        vgw.setLayout(layout);

        JsonViewUtils.setTagToWrapper(body, frameLayout, vgw);
        setStyle(JsonHelper.getStyles(body), frameLayout);

        setSubNode(JsonHelper.getSubNodes(body), vgw);
        return vgw;
    }


}
