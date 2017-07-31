package com.example.tn_ma_l30000048.myjsontest.parser;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.JsonUtils;
import com.example.tn_ma_l30000048.myjsontest.model.AbsolutePosition;
import com.example.tn_ma_l30000048.myjsontest.model.AbsoluteSize;
import com.example.tn_ma_l30000048.myjsontest.model.Layout;
import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;

import org.json.JSONObject;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonBasicWidget {
    static final String TAG="JsonBasicWidget";

    static void setAbsoluteLayoutParams(JSONObject json, View view, int parentWidth, int parentHeight){
        Layout layout= JsonUtils.decode(json.toString(), Layout.class);
        AbsolutePosition position=layout.absolutePosition;
        AbsoluteSize size=layout.absoluteSize;

        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        double w=0,h=0;

        if(size.width.baseOption==0) {
            w = parentWidth;
        }else if(size.width.baseOption==1)
            w=100;//self_width

        if(size.height.baseOption==1) {
            h = parentHeight;
        }else if(size.height.baseOption==0)
            h=100;//self_height

        w=size.width.offset+w*size.width.persentage;
        h=size.height.offset+h*size.height.persentage;
        w= DensityUtils.dp2px(view.getContext(),(float)w);
        h=DensityUtils.dp2px(view.getContext(),(float)h);

        layoutParams.width=(int)w;
        layoutParams.height=(int)h;

        double x=position.x.offset+position.x.persentage*w;
        double y=position.y.offset+position.y.persentage*h;
        x=DensityUtils.px2dp(view.getContext(),(float)x);
        y=DensityUtils.px2dp(view.getContext(),(float)y);
        view.setX((float)x);
        view.setY((float)y);
        System.out.println(TAG+" (pixel) x:"+x+" y:"+y+" w:"+w+" h"+h);

        view.setLayoutParams(layoutParams);
    }

    static void setRelativeLayoutParams(JSONObject json, View view, int parentWidth, int parentHeight){
        Layout layout= JsonUtils.decode(json.toString(), Layout.class);
        AbsolutePosition position=layout.absolutePosition;
        AbsoluteSize size=layout.absoluteSize;

        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        double w=0,h=0;

        if(size.width.baseOption==0) {
            w = parentWidth;
        }else if(size.width.baseOption==1)
            w=100;//self_width

        if(size.height.baseOption==1) {
            h = parentHeight;
        }else if(size.height.baseOption==0)
            h=100;//self_height

        w=size.width.offset+w*size.width.persentage;
        h=size.height.offset+h*size.height.persentage;
        w= DensityUtils.dp2px(view.getContext(),(float)w);
        h=DensityUtils.dp2px(view.getContext(),(float)h);

        layoutParams.width=(int)w;
        layoutParams.height=(int)h;

        double x=position.x.offset+position.x.persentage*w;
        double y=position.y.offset+position.y.persentage*h;
        x=DensityUtils.px2dp(view.getContext(),(float)x);
        y=DensityUtils.px2dp(view.getContext(),(float)y);
        view.setX((float)x);
        view.setY((float)y);
        System.out.println(TAG+" (pixel) x:"+x+" y:"+y+" w:"+w+" h"+h);

        view.setLayoutParams(layoutParams);
    }

    static void setAction(){

    }
}
