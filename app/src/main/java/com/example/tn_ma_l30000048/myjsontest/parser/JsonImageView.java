package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.tn_ma_l30000048.myjsontest.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonImageView {


    public static ImageView build(JSONObject body, Context context, int parentWidth, int parentHeight){
        ImageView imageView=new ImageView(context);
        System.out.println("----build ImageView----");
        JsonBasicWidget.setBasic(body, imageView);
        JsonBasicWidget.setAbsoluteLayoutParams(JsonHelper.getLayout(body),imageView,parentWidth,parentHeight);
        int picid = setImageStyle(JsonHelper.getStyles(body), imageView);
        imageView.setImageResource(picid);
        return imageView;
    }

    //这个之后要修改
    static int setImageStyle(JSONObject json, ImageView iv) {
        Iterator<String> keys=json.keys();
        int picId = -1;
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("cornerRadius")){
                    int cornerRadius=json.getInt(key);
                    int strokeWidth = 0; // 3dp 边框宽度
                    int fillColor = Color.TRANSPARENT;//内部填充颜色
                    GradientDrawable gd = new GradientDrawable();//创建drawable
                    gd.setColor(fillColor);
                    gd.setCornerRadius(cornerRadius);
                    iv.setBackground(gd);
                }else if(key.equalsIgnoreCase("contentMode")) {
                    int contentMode = json.getInt(key);
                    setImageScaleType(iv,contentMode);
                }else if(key.equalsIgnoreCase("imageSource")){
                    JSONObject imageRes = json.getJSONObject(key);
                    if (imageRes.getInt("dataType") == 0) {
                        String src = imageRes.getString("data");//TODO
                        picId = R.drawable.placeholder;
//                        Glide.with(iv.getContext()).load(R.drawable.placeholder).asBitmap().into(iv);
                    } else if (imageRes.getInt("dataType") == 1) {
                        JSONArray jsonArray = imageRes.getJSONArray("data");
                        List<String> srcs=new ArrayList<>();
                        for(int i=0;!jsonArray.isNull(i);i++){
                            srcs.add(jsonArray.getString(i));
                        }
                        picId = R.drawable.icon;
//                        Glide.with(iv.getContext()).load(R.drawable.icon).asBitmap().into(iv);
                    }
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
            e.printStackTrace();
        }
        return picId;
    }

    //    0:  UIViewContentModeScaleToFill, //fitXY
//    1:  UIViewContentModeScaleAspectFit,  fitCenter    // contents scaled to fit with fixed aspect. remainder is transparent
//    2:  UIViewContentModeScaleAspectFill,     // contents scaled to fill with fixed aspect. some portion of content may be clipped.
    static void setImageScaleType(ImageView imageView, int mode){
        switch (mode){
            case 0:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 1:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 2:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            default:
                break;
        }
    }
}
