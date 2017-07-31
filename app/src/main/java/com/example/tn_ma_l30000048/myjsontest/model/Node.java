package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/21.
 */

public class Node {
    public String tag;//int,用于区分同一页面上的各个View
    public String nodeName;
    public int nodeType;
    public Action action;//atomData 跳转openurl
    public Layout layout;
    public Styles styles;
    public List<Node> subNode;

    @Override
    public String toString() {
        return tag+" "+nodeName+" "+nodeType+" "+action.toString()+" "+layout.toString()+" "+ styles.toString()
                + "subnode size:"+ subNode.size();
    }
}
