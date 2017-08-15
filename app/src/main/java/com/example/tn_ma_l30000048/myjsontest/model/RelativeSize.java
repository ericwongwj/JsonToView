package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/8/3.
 */

public class RelativeSize {
    public RelativeCalculate width;
    public RelativeCalculate height;

    @Override
    public String toString() {
        return width.toString() + height.toString();
    }
}
