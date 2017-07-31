package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class Divider extends BaseView {
    static class Styles{
        String backgroundColor;
    }

    Styles styles;

    @Override
    public String toString() {
        return super.toString()+" "+styles.backgroundColor;
    }
}
