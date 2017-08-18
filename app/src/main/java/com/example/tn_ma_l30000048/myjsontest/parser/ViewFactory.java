package com.example.tn_ma_l30000048.myjsontest.parser;

import android.view.View;

import com.example.tn_ma_l30000048.myjsontest.model.Layout;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class ViewFactory {
    static final String TAG = ViewGroupFactory.class.getSimpleName();
    static int currentType;//0:absolute 1:relative

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper parent) {//, int parentWidth, int parentHeight
        JSONObject layoutJson = JsonHelper.getLayout(body);
        Layout layout = JsonUtils.decode(layoutJson.toString(), Layout.class);
        ViewWrapper viewWrapper = null;
        if(body.has("nodeType")){
            try {
                Integer type=(Integer) body.get("nodeType");
                switch (type){
                    case Constants.TYPE_TEXTVIEW:
                        viewWrapper = JsonTextView.build(body, parent);//, parentWidth, parentHeight
                        break;
                    case Constants.TYPE_IMAGE:
                        viewWrapper = JsonImageView.build(body, parent);//, parentWidth, parentHeight
                        break;
                    case Constants.TYPE_VIEW:
                        viewWrapper = buildView(body, parent);//, parentWidth, parentHeight
                        break;
//                    case Constants.TYPE_INDICATOR:
//                        viewWrapper = JsonLoadingView.build(body, context, parentWidth, parentHeight);
//                    break;
//                    case Constants.TYPE_RICHTEXT:
//                        viewWrapper = JsonTextView.buildTextView(body, context, parentWidth, parentHeight);
//                    break;
                    case Constants.TYPE_TABLEVIEW:
                        viewWrapper = JsonTableView.build(body, parent);//, parentWidth, parentHeight
                        break;
//                    case Constants.TYPE_SCROLLVIEW:
//                        System.out.println(TAG + "scroll view");
                    case Constants.TYPE_COLLECTIONVIEW:
                        viewWrapper = JsonGridView.build(body, parent);
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (viewWrapper != null)
            viewWrapper.setLayout(layout);
        return viewWrapper;
    }

    public static ViewWrapper buildView(JSONObject body, ViewGroupWrapper parent) {

        //如果是一个layout
        if (body.has("subNode"))
            return ViewGroupFactory.build(body, parent, parent.getContext());//, parentWidth, parentHeight

//        System.out.println("----buildView view-----");

        View view = new View(parent.getContext());
        ViewWrapper viewWrapper = new ViewWrapper(view);
        JsonViewUtils.setTagToWrapper(body, view, viewWrapper);
        //JsonViewUtils.setLayoutParams(JsonHelper.getLayout(body), view);//, parentWidth, parentHeight
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
