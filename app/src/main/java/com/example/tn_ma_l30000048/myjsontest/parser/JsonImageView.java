package com.example.tn_ma_l30000048.myjsontest.parser;

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

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper jsonRoot, int parentWidth, int parentHeight) {
        ViewWrapper viewWrapper = new ViewWrapper(jsonRoot.getContext());
        ImageView textView = buildImageView(body, viewWrapper, parentWidth, parentHeight);
        JsonViewUtils.setBasic(body, textView, viewWrapper);
        viewWrapper.setJsonView(textView);
        return viewWrapper;
    }

    public static ImageView buildImageView(JSONObject body, ViewWrapper viewWrapper, int parentWidth, int parentHeight) {
        ImageView imageView = new ImageView(viewWrapper.getContext());
        System.out.println("----build ImageView----");
        JsonViewUtils.setBasic(body, imageView, viewWrapper);
        JsonViewUtils.setAbsoluteLayoutParams(JsonHelper.getLayout(body), imageView, parentWidth, parentHeight);
        setImageStyle(body, imageView, viewWrapper);
        return imageView;
    }

    //这个之后要修改
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
                    setImageScaleType(iv,contentMode);
                }else if(key.equalsIgnoreCase("imageSource")){
                    AtomicData dataSource = new AtomicData();
                    JSONObject imgSrc = json.getJSONObject(key);
                    JsonViewUtils.setDataSource(imgSrc, dataSource);
                    viewWrapper.setDataSource(dataSource);
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
