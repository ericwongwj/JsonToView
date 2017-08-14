package com.example.tn_ma_l30000048.myjsontest.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonRoot;
import com.example.tn_ma_l30000048.myjsontest.parser.ViewWrapper;

import java.util.List;
import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    static final String TAG = BaseViewHolder.class.getSimpleName() + " ";

    JsonRoot mCellRoot;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mCellRoot = null;
    }

    public BaseViewHolder(JsonRoot viewRoot) {
        super(viewRoot.getJsonView());
        this.mCellRoot = viewRoot;
    }

    //用于加载头尾或者任何数据在rootDataMap中的view
    public void setUniqueData(Map<String, Object> rootDataMap) {
        System.out.println(TAG + "setUniqueData");
        for (ViewWrapper vw : mCellRoot.getSubViewWrappers()) {
            if (vw.getDataSource() != null) {
                AtomicData dataSource = vw.getDataSource();
                if (vw.getJsonView() instanceof TextView) {
                    if (dataSource.getDataType() == 0) {
                        ((TextView) vw.getJsonView()).setText(dataSource.getData());
                    } else if (dataSource.getDataType() == 1) {
                        List<String> keys = dataSource.getDataPaths();
                        String text = (String) JsonRoot.getDataFromMap(rootDataMap, keys);
                        ((TextView) vw.getJsonView()).setText(text);
                    }
                } else if (vw.getJsonView() instanceof ImageView) {
                    if (dataSource.getDataType() == 0) {//如何读取本地的图片？
                        ((ImageView) vw.getJsonView()).setImageResource(R.drawable.icon);
                    } else if (dataSource.getDataType() == 1) {
                        List<String> keys = dataSource.getDataPaths();//不嵌套 长度为0
                        String url = (String) JsonRoot.getDataFromMap(rootDataMap, keys);
                        Glide.with(mCellRoot.getContext()).load(url).asBitmap().into((ImageView) vw.getJsonView());
                    }
                }
            }
        }
    }

    public void setInsertData() {

    }

    //暂时不嵌套
    //cell是从adapter中传的map中拿数据 如果header的数据在map中则同样 如果在外面 那么直接在外面拿数据
    public void setCellData(Map<String, Object> map) {//遍历view树来set数据
        for (ViewWrapper vw : mCellRoot.getSubViewWrappers()) {
            if (vw.getDataSource() != null) {
                AtomicData dataSource = vw.getDataSource();
                if (vw.getJsonView() instanceof TextView) {
                    if (dataSource.getDataType() == 0) {
                        ((TextView) vw.getJsonView()).setText(dataSource.getData());
                    } else if (dataSource.getDataType() == 1) {
                        List<String> keys = dataSource.getDataPaths();
                        String text = (String) JsonRoot.getDataFromMap(map, keys);
                        ((TextView) vw.getJsonView()).setText(text);
                    }
                } else if (vw.getJsonView() instanceof ImageView) {
                    if (dataSource.getDataType() == 0) {//如何读取本地的图片？
                        ((ImageView) vw.getJsonView()).setImageResource(R.drawable.icon);
                    } else if (dataSource.getDataType() == 1) {
                        List<String> keys = dataSource.getDataPaths();//不嵌套 长度为0
                        String url = (String) JsonRoot.getDataFromMap(map, keys);
                        Glide.with(mCellRoot.getContext()).load(url).asBitmap().into((ImageView) vw.getJsonView());
                    }
                }
            }
        }
    }

}
