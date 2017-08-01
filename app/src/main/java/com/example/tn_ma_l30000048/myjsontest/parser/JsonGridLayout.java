package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.widget.GridLayout;

import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/8/1.
 */

public class JsonGridLayout {
    public static GridLayout build(JSONObject body, Context context, int parentWidth, int parentHeight) {
        GridLayout gridLayout = new GridLayout(context);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body), gridLayout, parentWidth, parentHeight);

        return gridLayout;
    }
}
