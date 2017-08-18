package com.example.tn_ma_l30000048.myjsontest.parser;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
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
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        System.out.println("Absolute layout " + " w:" + w + " h" + h);
        view.setLayoutParams(layoutParams);
    }


    public static void setRelativeLayoutParams(View view, Layout layout) {

        System.out.println("setRelativeLayoutParams");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativePosition position = layout.relativePosition;
        RelativeSize size = layout.relativeSize;
        int parentWidth = (int) DensityUtils.px2dp(view.getContext(), layout.parentWidth);
        int parentHeight = (int) DensityUtils.px2dp(view.getContext(), layout.parentHeight);
        double w=0,h=0;

        ViewGroup parent = (ViewGroup) view.getParent();//need check

        //TO SOLVE: 如果设置了上下左右 那么size会优先依赖与限制而不是这个size
        RelativeCalculate params;
        if (!isRelativeCalculateNull(position.top)) {
            params = position.top;
            layoutParams.topMargin = (int) params.offset;

            if (params.item == 0) {
                if (params.attribute == 0)//top里头只能相对于顶边 否则没有意义
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } else if (params.item != -1) {//-1则只设置本身的一个属性
                if (params.attribute == 0)
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP, params.item);
                else if (params.attribute == 2) {
                    View pivot = parent.findViewById(params.item);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) pivot.getLayoutParams();
                    if (lp.bottomMargin > 0)
                        layoutParams.topMargin -= lp.bottomMargin;
                    layoutParams.addRule(RelativeLayout.BELOW, params.item);
                } else
                    Log.e(TAG, "wrong top attribute!");
            }

        }
        if (!isRelativeCalculateNull(position.leading)) {
            params = position.leading;
            layoutParams.leftMargin = (int) params.offset;

            if (params.item == 0) {
                if (params.attribute == 1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
            } else if (params.item != -1) {
                if (params.attribute == 1)
                    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, params.item);
                else if (params.attribute == 3) {
                    View pivot = parent.findViewById(params.item);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) pivot.getLayoutParams();
                    if (lp.rightMargin > 0)
                        layoutParams.leftMargin -= lp.rightMargin;
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, params.item);
                    System.out.println(layoutParams.leftMargin);
                } else
                    Log.e(TAG, "wrong leading attribute!");
            }

        }
        if (!isRelativeCalculateNull(position.bottom)) {
            params = position.bottom;
            layoutParams.bottomMargin = (int) params.offset;
            if (params.item == 0) {
                if (params.attribute == 2) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                }
            } else if (params.item != -1) {
                //TODO:to test
                if (params.attribute == 0) {
                    View pivot = parent.findViewById(params.item);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) pivot.getLayoutParams();
                    if (lp.topMargin > 0)
                        layoutParams.bottomMargin -= lp.topMargin;
                    layoutParams.addRule(RelativeLayout.ABOVE, params.item);
                } else if (params.attribute == 2)
                    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, params.item);
                else
                    Log.e(TAG, "wrong top attribute!");
            }

        }
        if (!isRelativeCalculateNull(position.trailing)) {
            params = position.trailing;
            layoutParams.rightMargin = (int) params.offset;
            if (params.item == 0) {
                if (params.attribute == 3)
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } else if (params.item != -1) {
                if (params.attribute == 1) {
                    View pivot = parent.findViewById(params.item);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) pivot.getLayoutParams();
                    if (lp.leftMargin > 0)
                        layoutParams.rightMargin -= lp.leftMargin;
                    layoutParams.addRule(RelativeLayout.LEFT_OF, params.item);
                } else if (params.attribute == 3) {
                    layoutParams.addRule(RelativeLayout.ALIGN_LEFT, params.item);
                } else
                    Log.e(TAG, "wrong leading attribute!");
            }

        }
        if (!isRelativeCalculateNull(position.centerX)) {
//            params=position.centerX;
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,params.item);//要知道是在上还是下
        }
        if (!isRelativeCalculateNull(position.centerY)) {
//            params=position.centerY;
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);//要知道是左还是右
        }

        if (size == null) {
            w = ViewGroup.LayoutParams.WRAP_CONTENT;
            h = ViewGroup.LayoutParams.WRAP_CONTENT;
            Log.e(TAG, "size is null!");
        } else if (size.height.offset == -1 && size.width.offset == -1) {
//            w= RelativeLayout.LayoutParams.MATCH_PARENT;
//            h=RelativeLayout.LayoutParams.MATCH_PARENT;
            w = parentWidth;
            h = parentHeight;
        } else if (size.height.offset == -2 && size.width.offset == -2) {
            w = RelativeLayout.LayoutParams.WRAP_CONTENT;
            h = RelativeLayout.LayoutParams.WRAP_CONTENT;
        } else {
            w = size.width.offset;//这个默认为warp_content
            h = size.height.offset;
        }
        w = DensityUtils.dp2px(view.getContext(), (float) w);
        h = DensityUtils.dp2px(view.getContext(), (float) h);
        layoutParams.width = (int) w;
        layoutParams.height = (int) h;

        Log.d(TAG, "pW:" + parentWidth + " pH:" + parentHeight + " w:" + w + " h:" + h);
//        System.out.println("Relative layout (pixel) "+position.top.item+" "+position.leading.item);
        view.setLayoutParams(layoutParams);
    }

    private static boolean isRelativeCalculateNull(RelativeCalculate rc) {
        //这样做不太对吧
        return rc == null || (rc.attribute == 0 && rc.offset == 0 && rc.item == 0);
    }

    public static void setTagToWrapper(JSONObject json, View v, ViewWrapper viewWrapper) {
        Iterator<String> keys = json.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("tag")) {
                    String id = json.getString(key);//理论上可以作为界面中的id
                    viewWrapper.setTagId(Integer.parseInt(id));
                    v.setId(Integer.parseInt(id));
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
