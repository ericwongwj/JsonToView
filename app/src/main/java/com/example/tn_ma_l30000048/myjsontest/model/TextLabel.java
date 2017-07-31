package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class TextLabel extends BaseView{

    public TextLabelStyles styles;

    @Override
    public String toString() {
        return super.toString()+" "+ styles.toString();
    }
}
