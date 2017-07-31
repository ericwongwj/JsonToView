package com.example.tn_ma_l30000048.myjsontest.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class TableView extends BaseView {

    static class Styles{
        String backgroundColor;
        List<String> textHightLightRE;
        String textHightLightColor;
        List<String> textHightAction;
        TableStyle tableStyle;
    }

    static class TableStyle{
        List<String>dataPath;
        String cellTemplateName;
        int enablePullUpToLoadMore;
        int enablePullDownRefresh;
        String pullDownAction;
        String pullUpAction;
        String headView;
        String footView;
        InsertViews[] insertViews;
        int scrollDisabled;
    }

    static class InsertViews{
        String templateName;
        int row;
    }

    Styles styles;

    @Override
    public String toString() {
        return super.toString();
    }
}
