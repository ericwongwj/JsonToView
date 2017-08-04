package com.example.tn_ma_l30000048.myjsontest.parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/8/1.
 */

public class JsonLoadingView {
    public static View build(JSONObject body, Context context, int parentWidth, int parentHeight) {
        View recyclerView = new View(context);
        ProgressDialog pd = ProgressDialog.show(context, "this is pd", "loading...");
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body), recyclerView, parentWidth, parentHeight);

        pd.dismiss();
        return recyclerView;
    }

}
