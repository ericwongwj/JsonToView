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
    public int parentWidth;
    public int parentHeight;
    public RelativePosition relativePosition;
    public RelativeSize relativeSize;

    @Override
    public String toString() {
        return " "+strategy;
    }

    public void setWandH(int pw, int ph) {
        parentWidth = pw;
        parentHeight = ph;
    }

    static class Margin{
        double top;
        double bottom;
        double left;
        double right;
    }
}
