package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/25.
 */

public class Styles {
    public String backgroudColor;

    public AtomicData text;

    public String textColor;// string #rgba/#rgb
    public int textFontSize;
    public int textAlignment; //int 0:left; 1:center; 2:right
    public int numberOfLines;

    //如果两个正则表达式存在交集或者子集的关系，那么正则表达式数组后面的会覆盖前面的
    public List<String> textHightLightRE;
    public String textHightLightColor;//string #rgba/#rgb
    public List<String> textHightAction;//array<string> open url 与textHightLightRE相对应的跳转


    public AtomicData imageSource;//atomData

    public String backgroundColor;//string #rgba/#rgb
    public String borderColor;
    public double borderWidth;//float
    public double cornerRadius;

    public int contentMode;

    public int strickHeight;//int 0:no; 1:yes; no为不限死宽度，会根据文本内容重新计算宽度
    public int strickWidth;//int 0:no; 1:yes;
    public AtomicData dapendData;//atomData  显示与否的依赖数据，当依赖的数据为空或者为false的时候，view不显示
    public  String isHide;//string js code 需要返回bool类型，或者0、1

    public String shadowColor;
    public double shadowOpacity;
    public  AbsoluteSize shadowOffset;
    public double shadowRadius;

    public TableStyle tableStyle;//tableStyle
    public CollectionStyle  collectionStyle;//collectionStyle

    public String templateName;//当nodetype 为 10时，此node为自定义的view. templateName为自定义View模板的文件名。此时，style中除isHide有用外，别的都不起作用。
}
