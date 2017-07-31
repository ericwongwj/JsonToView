package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class AbsoluteSize {

    public AbsoluteCalculate width;
    public AbsoluteCalculate height;

    @Override
    public String toString() {
        return width.toString()+" "+height.toString();
    }
}
