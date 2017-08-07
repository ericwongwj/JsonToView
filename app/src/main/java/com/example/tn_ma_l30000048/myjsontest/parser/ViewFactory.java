package com.example.tn_ma_l30000048.myjsontest.parser;

import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class ViewFactory {
    static final String TAG = "ViewFactory ";

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        if(body.has("nodeType")){
            try {
                Integer type=(Integer) body.get("nodeType");
                switch (type){
                    case Constants.TYPE_TEXTVIEW:
                        return JsonTextView.build(body, parent, parentWidth, parentHeight);
                    case Constants.TYPE_IMAGE:
                        return JsonImageView.build(body, parent, parentWidth, parentHeight);
                    case Constants.TYPE_VIEW:
                        return buildView(body, parent, parentWidth, parentHeight);
//                    case Constants.TYPE_INDICATOR:
//                        return JsonLoadingView.build(body, context, parentWidth, parentHeight);
//                    case Constants.TYPE_RICHTEXT:
//                        return JsonTextView.buildTextView(body, context, parentWidth, parentHeight);
                    case Constants.TYPE_TABLEVIEW:
                        return JsonTableView.build(body, parent, parentWidth, parentHeight);
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

    public static ViewWrapper buildView(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {

        //如果是一个layout
        if (body.has("subNode"))
            return ViewGroupFactory.build(parent, body, parentWidth, parentHeight);

        System.out.println("----buildView view-----");

        View view = new View(parent.getContext());
        JsonBasicWidget.setBasic(body, view);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),view,parentWidth,parentHeight);
        JSONObject style=JsonHelper.getStyles(body);
        String color= null;
        try {
            color = style.getString("backgroundColor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.setBackgroundColor(JasonHelper.parseColor(color));

        ViewWrapper vw = new ViewWrapper(view, parent);
        parent.appendView(vw);

        return vw;
    }


}
