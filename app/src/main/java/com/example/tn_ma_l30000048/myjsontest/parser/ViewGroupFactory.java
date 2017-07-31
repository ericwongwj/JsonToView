package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tn_ma_l30000048.myjsontest.JsonUtils;
import com.example.tn_ma_l30000048.myjsontest.model.Layout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class ViewGroupFactory {
    public static final String TAG="ViewGroupFactory";

    public static ViewGroup build(JSONObject body, Context context, int parentWidth, int parentHeight) {
        Layout layout = JsonUtils.decode(body.toString(), Layout.class);
        ViewGroup viewGroup=null;
        if (layout.strategy == 0)
            viewGroup = buildFrameLayout(body, context, parentWidth,parentHeight);
        else if (layout.strategy == 1)
            viewGroup = buildRelativeLayout(body, context, parentWidth,parentHeight);

        return viewGroup;
    }

    static FrameLayout buildFrameLayout(JSONObject body, Context context, int parentWidth, int parentHeight){
        System.out.println("build FrameLayout");
        FrameLayout frameLayout=new FrameLayout(context);
//        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),frameLayout,parentWidth,parentHeight);
        //暂时先设为适配父容器
        System.out.println("p="+parentWidth+"   "+parentHeight);
        ViewGroup.LayoutParams layoutParams=new FrameLayout.LayoutParams(parentWidth,parentHeight);
        frameLayout.setLayoutParams(layoutParams);
        setStyle(JsonHelper.getStyles(body),frameLayout);
        setSubNode(JsonHelper.getSubNodes(body),frameLayout);
        return frameLayout;
    }

    static RelativeLayout buildRelativeLayout(JSONObject body, Context context, int parentWidth, int parentHeight){
        RelativeLayout relativeLayout=new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setStyle(JsonHelper.getStyles(body),relativeLayout);
        setSubNode(JsonHelper.getSubNodes(body),relativeLayout);
        relativeLayout.setLayoutParams(layoutParams);
        return relativeLayout;
    }


    static void setStyle(JSONObject json, ViewGroup vg){
        Iterator<String> keys=json.keys();
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("backgroundColor")){
                    System.out.println(json.getString(key));
                    int color=Color.parseColor(json.getString(key));
                    System.out.println(color);
                    vg.setBackgroundColor(color);
                }
                //TODO more attributes to add
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    static void setSubNode(JSONArray nodes, ViewGroup viewGroup){
        if(!nodes.isNull(0)){
            int index=0;
            try {
                while(!nodes.isNull(index)){
                    JSONObject nodeBody=nodes.getJSONObject(index);
                    View subView=ViewFactory.build(nodeBody,viewGroup.getContext(),viewGroup.getLayoutParams().width,viewGroup.getLayoutParams().height);
                    viewGroup.addView(subView);
                    index++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
