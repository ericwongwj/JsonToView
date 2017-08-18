package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/8/3.
 */

public class RelativeCalculate extends AbsoluteCalculate {
    public int item;
    public int attribute;//0:top  1:leading  2:bottom 3:trailing 4:centerX 5:centerY
    // public int relationship;// NSLayoutRelationLessThanOrEqual = -1,
    //NSLayoutRelationEqual = 0,  NSLayoutRelationGreaterThanOrEqual = 1
    // public double multiplier;

    @Override
    public String toString() {
        return "offset:" + offset + " attr:" + attribute + " item:" + item;
    }

}
