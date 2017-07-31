package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class Layout {
    public int strategy;
//0:AbsoluteLayout; 1:RelativeLayout; 2:HorizonLinearLayout; 3:VerticalLinearLayout;

    public AbsolutePosition absolutePosition;
    public AbsoluteSize absoluteSize;
    public Margin margin;
    public Margin padding;

    @Override
    public String toString() {
        return " "+strategy;
    }

    static class Margin{
        double top;
        double bottom;
        double left;
        double right;
    }
}
