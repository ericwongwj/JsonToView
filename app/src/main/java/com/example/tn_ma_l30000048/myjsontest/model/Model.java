package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/25.
 */

public class Model {
    String SDKVersion;
    String ModuleVersion;
    int containerType;//0:view  1:page 容器为viewcontroller 占据整个屏幕 目前没用都写0
    String containerHeight;//直接返回string类型数值，或者使用"{{js code}}"通过计算返回  主要用于table cell or table insertView，这两者的高度都是由自己内容决定，需要动态计算。
    String registerProperty;//必需是一个JS CODE
    Node rootNode;
    HeaderInfo headerInfo;
    RequestInfo requestInfo;

    @Override
    public String toString() {
        return "containerType:"+containerType+" rootNode:"+rootNode.nodeName;
        //        Model model=gson.fromJson(jsonStrings)
    }
}
