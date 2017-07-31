package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class Action {
    int dataType;
    String data;//openUrl
//    ios通过将tap gesture绑定到view上。所以，所有的view都可以添加点击事件。
//    当atomData的dataType为0时，支持使用js code
    @Override
    public String toString() {
        return dataType+" "+data;
    }
}
