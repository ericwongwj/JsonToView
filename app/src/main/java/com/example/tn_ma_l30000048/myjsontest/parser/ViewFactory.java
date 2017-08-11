package com.example.tn_ma_l30000048.myjsontest.parser;

import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class ViewFactory {
    static final String TAG = ViewGroupFactory.class.getSimpleName();
    static int currentType;//0:absolute 1:relative

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {
        if(body.has("nodeType")){
            try {
                Integer type=(Integer) body.get("nodeType");
                switch (type){
                    case Constants.TYPE_TEXTVIEW:
                        return JsonTextView.build(body, jsonRoot, parentWidth, parentHeight);
                    case Constants.TYPE_IMAGE:
                        return JsonImageView.build(body, jsonRoot, parentWidth, parentHeight);
                    case Constants.TYPE_VIEW:
                        return buildLine(body, jsonRoot, parentWidth, parentHeight);
//                    case Constants.TYPE_INDICATOR:
//                        return JsonLoadingView.build(body, context, parentWidth, parentHeight);
//                    case Constants.TYPE_RICHTEXT:
//                        return JsonTextView.buildTextView(body, context, parentWidth, parentHeight);
                    case Constants.TYPE_TABLEVIEW:
                        return JsonTableView.build(body, jsonRoot, parentWidth, parentHeight);
//                    case Constants.TYPE_SCROLLVIEW:
//                        System.out.println(TAG + "scroll view");
//                    case Constants.TYPE_COLLECTIONVIEW:
//                        return JsonGridView.build(body, context, parentWidth, parentHeight);
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ViewWrapper buildLine(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {

        //如果是一个layout
        if (body.has("subNode"))
            return ViewGroupFactory.build(body, jsonRoot, parentWidth, parentHeight);

//        System.out.println("----buildLine view-----");

        View view = new View(jsonRoot.getContext());
        ViewWrapper viewWrapper = new ViewWrapper(view);
        JsonViewUtils.setTagToWrapper(body, view, viewWrapper);
        JsonViewUtils.setLayoutParams(JsonHelper.getLayout(body), view, parentWidth, parentHeight);
        JSONObject style=JsonHelper.getStyles(body);
        String color= null;
        try {
            color = style.getString("backgroundColor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.setBackgroundColor(JasonHelper.parseColor(color));

        return viewWrapper;
    }


}
