package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class RichText extends BaseView{

    static class Styles{
        List<String> textHightLightRE;
        String textHightLightColor;
        List<String> textHightAction;
        Text text;
    }

    static class Text {
        int dataType;
        List<String> data;
    }

    Styles styles;



    @Override
    public String toString() {
        return super.toString()+" "+styles.textHightLightColor;
    }
}
