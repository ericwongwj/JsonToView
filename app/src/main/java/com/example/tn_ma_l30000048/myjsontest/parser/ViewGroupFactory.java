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
    public static final String TAG="ViewGroupFactory";

//    //为JsonRoot设计
//    public static ViewGroupWrapper build(Context context, JSONObject body, int parentWidth, int parentHeight) {
//        ViewGroup vg = buildViewGroup(body, context, parentWidth, parentHeight);
//        return new ViewGroupWrapper(vg, null);
//    }

    public static ViewGroupWrapper build(ViewGroupWrapper parent, JSONObject body, int parentWidth, int parentHeight) {
        ViewGroup vg = buildViewGroup(body, parent, parentWidth, parentHeight);
        ViewGroupWrapper vgw = new ViewGroupWrapper(vg, parent);
        parent.appendView(vgw);
        return new ViewGroupWrapper(vg, parent);
    }

    private static ViewGroup buildViewGroup(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        Layout layout = JsonUtils.decode(body.toString(), Layout.class);
        ViewGroup viewGroup=null;
        if (layout.strategy == 0)
            viewGroup = buildFrameLayout(body, parent, parentWidth, parentHeight);
        else if (layout.strategy == 1)
            viewGroup = buildRelativeLayout(body, parent, parentWidth, parentHeight);

        return viewGroup;
    }

    private static FrameLayout buildFrameLayout(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        System.out.println("----buildViewGroup FrameLayout----");
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        JsonBasicWidget.setBasic(body, frameLayout);

//        System.out.println("parent w=" + parentWidth + "   h=" + parentHeight);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body), frameLayout, parentWidth, parentHeight);
        setStyle(JsonHelper.getStyles(body),frameLayout);
        setSubNode(JsonHelper.getSubNodes(body), frameLayout, parent);
        return frameLayout;
    }

    private static RelativeLayout buildRelativeLayout(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        RelativeLayout relativeLayout = new RelativeLayout(parent.getContext());
        JsonBasicWidget.setBasic(body, relativeLayout);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body), relativeLayout, parentWidth, parentHeight);
        setStyle(JsonHelper.getStyles(body),relativeLayout);
        setSubNode(JsonHelper.getSubNodes(body), relativeLayout, parent);
        return relativeLayout;
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

    private static void setSubNode(JSONArray nodes, ViewGroup viewGroup, ViewGroupWrapper parent) {
        if(!nodes.isNull(0)){
            int index=0;
            try {
                while(!nodes.isNull(index)){
                    JSONObject nodeBody=nodes.getJSONObject(index);
                    ViewWrapper subViewWrapper = ViewFactory.build(nodeBody, parent, viewGroup.getLayoutParams().width, viewGroup.getLayoutParams().height);
                    //TODO 容易出空指针
                    viewGroup.addView(subViewWrapper.getJsonView());
                    parent.subViewWrappers.add(subViewWrapper);
                    index++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
