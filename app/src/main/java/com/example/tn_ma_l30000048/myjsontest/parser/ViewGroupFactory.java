package com.example.tn_ma_l30000048.myjsontest.parser;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.tn_ma_l30000048.myjsontest.model.Layout;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/7/31.
 */

public class ViewGroupFactory {
    static final String TAG = ViewGroupFactory.class.getSimpleName() + " ";

    static int currentType;//0:absolute 1:relative

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper parent) {//, int parentWidth, int parentHeight
        Layout layout = JsonUtils.decode(JsonHelper.getLayout(body), Layout.class);
        ViewGroupWrapper vw = null;
        if (layout.strategy == 0) {
            vw = buildFrameLayout(body, parent);//, parentWidth, parentHeight
            vw.setLayout(layout);
            currentType = ViewFactory.currentType = 0;
        } else if (layout.strategy == 1) {
            vw = buildRelativeLayout(body, parent);//, parentWidth, parentHeight
            vw.setLayout(layout);
            currentType = ViewFactory.currentType = 1;
        }
        return vw;
    }

    private static ViewGroupWrapper buildFrameLayout(JSONObject body, ViewGroupWrapper parent) {//, int parentWidth, int parentHeight)
        System.out.println("----buildViewGroup FrameLayout----");
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        ViewGroupWrapper viewWrapper = new ViewGroupWrapper(frameLayout);
        JsonViewUtils.setTagToWrapper(body, frameLayout, viewWrapper);
        //长宽需要先计算

//        JsonViewUtils.setLayoutParams(JsonHelper.getLayout(body), frameLayout, parentWidth, parentHeight);
        setStyle(JsonHelper.getStyles(body),frameLayout);
        setSubNode(JsonHelper.getSubNodes(body), frameLayout, parent);
        return viewWrapper;
    }

    private static ViewGroupWrapper buildRelativeLayout(JSONObject body, ViewGroupWrapper parent) {//, int parentWidth, int parentHeight
        System.out.println("----buildViewGroup RelativeLayout----");
        RelativeLayout frameLayout = new RelativeLayout(parent.getContext());
        ViewGroupWrapper viewWrapper = new ViewGroupWrapper(parent.getContext());
        JsonViewUtils.setTagToWrapper(body, frameLayout, viewWrapper);

//        JsonViewUtils.setLayoutParams(JsonHelper.getLayout(body), frameLayout, parentWidth, parentHeight);
        setStyle(JsonHelper.getStyles(body), frameLayout);
        setSubNode(JsonHelper.getSubNodes(body), frameLayout, parent);
        return viewWrapper;
    }

    private static void setStyle(JSONObject json, ViewGroup vg) {
        Iterator<String> keys=json.keys();
        try{
            while(keys.hasNext()){
                String key=keys.next();
                if(key.equalsIgnoreCase("backgroundColor")){
                    int color = JasonHelper.parseColor(json.getString(key));
                    vg.setBackgroundColor(color);
                }
                //TODO more attributes to add
            }
        }catch (Exception e){
            Log.e(e.getClass().getSimpleName(),e.getMessage());
        }
    }

    private static void setSubNode(JSONArray nodes, ViewGroup viewGroup, ViewGroupWrapper parent) {
        try {
            for (int i = 0; i < nodes.length(); i++) {
                System.out.println(TAG + "setSubNode " + i);
                JSONObject nodeBody = nodes.getJSONObject(i);
                ViewWrapper subViewWrapper = ViewFactory.build(nodeBody, parent);//, viewGroup.getLayoutParams().width, viewGroup.getLayoutParams().he
                //TODO 容易出空指针
                if (subViewWrapper.getJsonView() == null) {
                    throw new NullPointerException("mJsonView is null");
                }
                viewGroup.addView(subViewWrapper.getJsonView());
                parent.appendView(subViewWrapper);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
