package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonImageView {

    static final String TAG = JsonImageView.class.getSimpleName();

    public static ViewWrapper build(JSONObject body, Context context) {
        ViewWrapper viewWrapper = new ViewWrapper(context);
        ImageView imageView = buildImageView(body, viewWrapper);
        JsonViewUtils.setTagToWrapper(body, imageView, viewWrapper);
        viewWrapper.setJsonView(imageView);
        return viewWrapper;
    }

    public static ImageView buildImageView(JSONObject body, ViewWrapper viewWrapper) {
        ImageView imageView = new ImageView(viewWrapper.getContext());
        System.out.println("----build ImageView----");
        if (JsonViewUtils.isJsonValid(body, Constants.TYPE_TEXTVIEW)) {
            JsonViewUtils.setAction();
            setImageStyle(JsonHelper.getStyles(body), imageView, viewWrapper);
        }
        return imageView;
    }

    static void setImageStyle(JSONObject json, ImageView iv, ViewWrapper viewWrapper) {
        Iterator<String> keys=json.keys();
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
                    setImageScaleType(iv, contentMode);//需要确定一下f
                }else if(key.equalsIgnoreCase("imageSource")){
                    JSONObject imgSrc = json.getJSONObject(key);
                    AtomicData dataSource = JsonViewUtils.parseDataSource(imgSrc);
                    viewWrapper.setDataSource(dataSource);
                } else if (key.equalsIgnoreCase("placeHolder")) {
                    String placeHolder = json.getString(key);
                    int resId = iv.getContext().getResources().getIdentifier(placeHolder, "drawable", Constants.PACKAGE_NAME);
                    iv.setImageResource(resId);
                }
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
            e.printStackTrace();
        }
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
