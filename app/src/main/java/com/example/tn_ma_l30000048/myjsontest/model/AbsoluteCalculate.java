package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/25.
 */

public class AbsoluteCalculate {
    public double offset;
    public double persentage;//float 相对屏幕百分比
    public int baseOption;//int 0:parentWidth; 1：parentHeight; 2:selfWidth; 3:selfHeight

    @Override
    public String toString() {
        return "offset:"+offset+" percentage:"+persentage+" baseOption:"+baseOption;
    }
}
