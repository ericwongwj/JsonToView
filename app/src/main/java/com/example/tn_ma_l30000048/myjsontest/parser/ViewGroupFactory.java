package com.example.tn_ma_l30000048.myjsontest.parser;

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

    //只供第一次JsonRoot调用
    public static ViewWrapper build(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        Layout layout = JsonUtils.decode(body.toString(), Layout.class);
        ViewGroup viewGroup=null;
        ViewWrapper vw = null;
        if (layout.strategy == 0)
            vw = buildFrameLayout(body, parent, parentWidth, parentHeight);
        else if (layout.strategy == 1)
            vw = buildRelativeLayout(body, parent, parentWidth, parentHeight);
        return vw;
    }

    private static ViewWrapper buildFrameLayout(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {
        System.out.println("----buildViewGroup FrameLayout----");
        FrameLayout frameLayout = new FrameLayout(jsonRoot.getContext());
        ViewWrapper viewWrapper = new ViewWrapper(jsonRoot.getContext());
        JsonViewUtils.setBasic(body, frameLayout, viewWrapper);

//        System.out.println("parent w=" + parentWidth + "   h=" + parentHeight);
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), frameLayout, parentWidth, parentHeight);
        setStyle(JsonHelper.getStyles(body),frameLayout);
        setSubNode(JsonHelper.getSubNodes(body), frameLayout, jsonRoot);
        return viewWrapper;
    }

    private static ViewWrapper buildRelativeLayout(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {
        System.out.println("----buildViewGroup RelativeLayout----");
        RelativeLayout frameLayout = new RelativeLayout(jsonRoot.getContext());
        ViewWrapper viewWrapper = new ViewWrapper(jsonRoot.getContext());
        JsonViewUtils.setBasic(body, frameLayout, viewWrapper);

//        System.out.println("parent w=" + parentWidth + "   h=" + parentHeight);
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), frameLayout, parentWidth, parentHeight);
        setStyle(JsonHelper.getStyles(body), frameLayout);
        setSubNode(JsonHelper.getSubNodes(body), frameLayout, jsonRoot);
        return viewWrapper;
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

    private static void setSubNode(JSONArray nodes, ViewGroup viewGroup, ViewGroupWrapper jsonRoot) {
        if(!nodes.isNull(0)){
            int index=0;
            try {
                while(!nodes.isNull(index)){
                    JSONObject nodeBody=nodes.getJSONObject(index);
                    ViewWrapper subViewWrapper = ViewFactory.build(nodeBody, jsonRoot, viewGroup.getLayoutParams().width, viewGroup.getLayoutParams().height);
                    //TODO 容易出空指针
                    viewGroup.addView(subViewWrapper.getJsonView());

                    jsonRoot.appendView(subViewWrapper);
                    index++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
