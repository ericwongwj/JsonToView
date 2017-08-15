package com.example.tn_ma_l30000048.myjsontest.parser;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tn_ma_l30000048.myjsontest.model.AbsolutePosition;
import com.example.tn_ma_l30000048.myjsontest.model.AbsoluteSize;
import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.model.Layout;
import com.example.tn_ma_l30000048.myjsontest.model.RelativeCalculate;
import com.example.tn_ma_l30000048.myjsontest.model.RelativePosition;
import com.example.tn_ma_l30000048.myjsontest.model.RelativeSize;
import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class JsonViewUtils {
    static final String TAG = "JsonViewUtils";

    public static void setLayoutParams(View view, Layout layout) {
        if (layout.absolutePosition != null)
            setAbsoluteLayoutParams(view, layout);
        else
            setRelativeLayoutParams(view, layout);
    }

    public static void setAbsoluteLayoutParams(View view, Layout layout) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        AbsolutePosition position = layout.absolutePosition;
        AbsoluteSize size = layout.absoluteSize;
        int parentWidth = layout.parentWidth;
        int parentHeight = layout.parentHeight;

        double w=0,h=0;
        boolean isWNeedH = false;

        if(size.width.baseOption==0) {
            w = DensityUtils.px2dp(view.getContext(), (float) parentWidth);
            w = size.width.offset + w * size.width.persentage;
        } else if (size.width.baseOption == 3)
            isWNeedH = true;

        if(size.height.baseOption==1) {
            h = DensityUtils.px2dp(view.getContext(), (float) parentHeight);
            h = size.height.offset + h * size.height.persentage;
        } else if (size.height.baseOption == 2)
            h = size.height.persentage * w;

        if (isWNeedH)
            w = size.width.persentage * h;

        w= DensityUtils.dp2px(view.getContext(),(float)w);
        h = DensityUtils.dp2px(view.getContext(), (float) h);

        layoutParams.width=(int)w;
        layoutParams.height=(int)h;

        float parentW = DensityUtils.px2dp(view.getContext(), (float) parentWidth);
        float parentH = DensityUtils.px2dp(view.getContext(), (float) parentHeight);

        //之后这里也有可能出问题
        double x = position.x.offset + position.x.persentage * parentW;
        double y = position.y.offset + position.y.persentage * parentH;
        x = DensityUtils.dp2px(view.getContext(), (float) x);
        y = DensityUtils.dp2px(view.getContext(), (float) y);
        view.setX((float)x);
        view.setY((float)y);
//        System.out.println("parentW:"+parentWidth+" parentH:"+parentHeight);
//        System.out.println("position:"+position);
//        System.out.println("size:"+size);
//        System.out.println("Absolute layout (pixel) x:" + x + " y:" + y + " w:" + w + " h" + h);
        view.setLayoutParams(layoutParams);
    }


    public static void setRelativeLayoutParams(View view, Layout layout) {

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativePosition position = layout.relativePosition;
        RelativeSize size = layout.relativeSize;
        int parentWidth = layout.parentWidth;
        int parentHeight = layout.parentHeight;
        double w=0,h=0;

        RelativeCalculate params;
        if (position.top != null) {
            params = position.top;
            layoutParams.topMargin = (int) params.offset;
            layoutParams.addRule(RelativeLayout.BELOW, params.item);
        }
        if (position.leading != null) {
            params = position.leading;
            layoutParams.leftMargin = (int) params.offset;
            layoutParams.addRule(RelativeLayout.RIGHT_OF, params.item);

        }
        if (position.bottom != null) {
            params = position.bottom;
            layoutParams.bottomMargin = (int) params.offset;
            layoutParams.addRule(RelativeLayout.ABOVE, params.item);

        }
        if (position.trailing != null) {
            params = position.trailing;
            layoutParams.rightMargin = (int) params.offset;
            layoutParams.addRule(RelativeLayout.LEFT_OF, params.item);
        }
        if (position.centerX != null) {
//            params=position.centerX;
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,params.item);//要知道是在上还是下
        }
        if (position.centerY != null) {
//            params=position.centerY;
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);//要知道是左还是右
        }

        if (size == null) {
            w = parentWidth - layoutParams.leftMargin - layoutParams.rightMargin;
            h = parentHeight - layoutParams.topMargin - layoutParams.bottomMargin;
        } else {
            w = size.width.offset;//这个宽度需要怎么定义？
            h = size.height.offset;
        }
        layoutParams.width = (int) w;
        layoutParams.height = (int) h;

        view.setLayoutParams(layoutParams);
    }


    public static void setTagToWrapper(JSONObject json, View v, ViewWrapper viewWrapper) {
        Iterator<String> keys = json.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("tag")) {
                    String id = json.getString(key);//理论上可以作为界面中的id
                    viewWrapper.setTagId(Integer.parseInt(id));
                } else if (key.equalsIgnoreCase("nodeName")) {
                    String name = json.getString(key);
//                    v.setTag(name);
                    viewWrapper.setNodeName(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setTagToWrapper(JSONObject json, View v, SparseArray<View> viewMap, SparseArray<String> nameMap) {
        Iterator<String> keys = json.keys();
        try {
            String id = null;
            String name = null;
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("tag")) {
                    id = json.getString(key);//理论上可以作为界面中的id
                } else if (key.equalsIgnoreCase("nodeName")) {
                    name = json.getString(key);
                    v.setTag(name);
                }
            }//json中这两个应该都不为空
            viewMap.put(Integer.parseInt(id), v);
            nameMap.put(Integer.parseInt(id), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAction() {

    }

    public static AtomicData parseDataSource(JSONObject jsonObject) throws JSONException {
        AtomicData dataSource = new AtomicData();
        if (jsonObject.getInt("dataType") == 0) {
            dataSource.setDataType(0);
            dataSource.setData(jsonObject.getString("data"));
        } else if (jsonObject.getInt("dataType") == 1) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<String> paths = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                paths.add(jsonArray.getString(i));
            }
            dataSource.setDataType(1);
            dataSource.setDataPaths(paths);
        }
        return dataSource;
    }

    public static AtomicData parseDataSource(JSONArray jsonArray) throws JSONException {
        AtomicData dataSource = new AtomicData();
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            paths.add(jsonArray.getString(i));
        }
        dataSource.setDataType(1);
        dataSource.setDataPaths(paths);
        return dataSource;
    }
}
