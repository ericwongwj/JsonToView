package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.JasonHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class ViewFactory {
    static final String TAG="ViewFactory";

    public static View build(JSONObject body, Context context, int parentWidth, int parentHeight){
        if(body.has("nodeType")){
            try {
                Integer type=(Integer) body.get("nodeType");
                switch (type){
                    case Constants.TYPE_TEXTVIEW:
                        return JsonTextView.build(body,context,parentWidth,parentHeight);
                    case Constants.TYPE_IMAGE:
                        return JsonImageView.build(body,context,parentWidth,parentHeight);
                    case Constants.TYPE_VIEW:
                        return buildView(body,context,parentWidth,parentHeight);//如果不考虑嵌套 view只有可能生成一条直线之类的
                    case Constants.TYPE_INDICATOR:
                        return JsonLoadingView.build(body, context, parentWidth, parentHeight);
                    case Constants.TYPE_TABLEVIEW:
                        return JsonListView.build(body,context,parentWidth,parentHeight);
                    case Constants.TYPE_COLLECTIONVIEW:
                        return JsonGridLayout.build(body, context, parentWidth, parentHeight);
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static View buildView(JSONObject body, Context context, int parentWidth, int parentHeight){
        View view=new View(context);

        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),view,parentWidth,parentHeight);
        JSONObject style=JsonHelper.getStyles(body);
        String color= null;
        try {
            color = style.getString("backgroundColor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.setBackgroundColor(JasonHelper.parse_color(color));
        return view;
    }


}
