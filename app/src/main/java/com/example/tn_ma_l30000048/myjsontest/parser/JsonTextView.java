package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonTextView {

    static final String TAG = "JsonTextView";

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper parent, int parentWidth, int parentHeight) {
        TextView textView = buildTextView(body, parent.getContext(), parentWidth, parentHeight);
        return new ViewWrapper(textView, parent);
    }

    //暂时先不判断json是否有效
    public static TextView buildTextView(JSONObject body, Context context, int parentWidth, int parentHeight) {
        TextView textView=new TextView(context);
        System.out.println("----buildViewGroup textView----");
        JsonBasicWidget.setBasic(body, textView);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),textView,parentWidth,parentHeight);
        setTextStyle(JsonHelper.getStyles(body),textView);
        return textView;
    }


    static void setTextStyle(JSONObject json,TextView tv){
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
                    JSONObject text=json.getJSONObject(key);
                    if(text.getInt("dataType")==0){
                        String str=text.getString("data");
                        tv.setText(str);
                    }else if(text.getInt("dataType")==1){
                        JSONArray jsonArray=text.getJSONArray("data");
                        //这里的数据从网络请求到的数据
                        List<String> strs=new ArrayList<>();
                        for(int i=0;!jsonArray.isNull(i);i++){
                            strs.add(jsonArray.getString(i));
                        }
                        tv.setText("data:" + strs.get(0));
                    }
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }

    }
}
