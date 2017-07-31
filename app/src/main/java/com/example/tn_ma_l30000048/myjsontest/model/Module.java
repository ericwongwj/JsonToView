package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/21.
 */

public class Module {
    public String SDKVersion;
    public String ModuleVersion;
    public int containerType;
    public Node rootNode;

    @Override
    public String toString() {
        return SDKVersion+" "+ModuleVersion+" "+containerType+" "+rootNode.toString();
    }
}
