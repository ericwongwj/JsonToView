package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class BaseView {
    public String tag;
    public String nodeName;
    public String nodeType;
    public Layout layout;

    @Override
    public String toString() {
        return tag+" "+nodeName+" "+nodeType+" "+layout.toString();
    }
}
