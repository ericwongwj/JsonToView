package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class AbsolutePosition {
    public AbsoluteCalculate x;
    public AbsoluteCalculate y;

//    xy的百分比都是相对父容器的
    @Override
    public String toString() {
        return x.toString()+" "+y.toString();
    }
}
