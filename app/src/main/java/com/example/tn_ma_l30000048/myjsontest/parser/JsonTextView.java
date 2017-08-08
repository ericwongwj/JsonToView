package com.example.tn_ma_l30000048.myjsontest.parser;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonTextView {

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {
        ViewWrapper viewWrapper = new ViewWrapper(jsonRoot.getContext());
        TextView textView = buildTextView(body, viewWrapper, parentWidth, parentHeight);
        JsonViewUtils.setBasic(body, textView, viewWrapper);
        viewWrapper.setJsonView(textView);
        return viewWrapper;
    }

    //暂时先不判断json是否有效
    public static TextView buildTextView(JSONObject body, ViewWrapper viewWrapper, int parentWidth, int parentHeight) {
        TextView textView = new TextView(viewWrapper.getContext());
        System.out.println("----build TextView----");
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), textView, parentWidth, parentHeight);
        JsonViewUtils.setAction();
        setTextStyle(JsonHelper.getStyles(body), textView, viewWrapper);
        return textView;
    }


    private static void setTextStyle(JSONObject json, TextView tv, ViewWrapper viewWrapper) {
        Iterator<String> keys=json.keys();
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("textColor")){
                    String color=json.getString(key);
                    tv.setTextColor(JasonHelper.parseColor(color));
                }else if(key.equalsIgnoreCase("backgroundColor")){
                    String color=json.getString(key);
                    tv.setBackgroundColor(JasonHelper.parseColor(color));
                }else if(key.equalsIgnoreCase("textFontSize")){
                    int fontSize=json.getInt(key);
                    tv.setTextSize(fontSize);
                } else if (key.equalsIgnoreCase("textAlignment")) {
                    switch (json.getInt(key)) {
                        case 0:
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                            break;
                        case 1:
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            break;
                        case 2:
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                            break;
                    }
                } else if (key.equalsIgnoreCase("numberOfLines")) {
                    int lines=json.getInt(key);
                    tv.setLines(lines);
                }else if(key.equalsIgnoreCase("strickHeight")){
                    int strickHeight=json.getInt(key);
//                    if(strickHeight==0)
//                        System.out.println(TAG+" resize height for text");
//                    else if(strickHeight==1)
//                        System.out.println(TAG+"strick height");
                }else if(key.equalsIgnoreCase("strickWidth")){
                    int strickWidth=json.getInt(key);
//                    if(strickWidth==0)
//                        System.out.println(TAG+"resize width for text");
//                    else if(strickWidth==1)
//                        System.out.println(TAG+"strick width");
                }else if(key.equalsIgnoreCase("isHide")){
                    String jsCode=json.getString(key);
                    tv.setVisibility(View.VISIBLE);
                }else if(key.equalsIgnoreCase("text")){
                    AtomicData dataSource = new AtomicData();
                    JSONObject text=json.getJSONObject(key);
                    JsonViewUtils.setDataSource(text, dataSource);
                    viewWrapper.setDataSource(dataSource);
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }

    }
}
