package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/25.
 */

/**
 * notice:
 * insertViews的数据源独立，而cellTemplateName的数据从根容器中传入。
 * 由于下拉和上拉会有请求数据操作，造成请求数据参数可变，此时应通过registerProperty将可变参数注册为全局变量。
 * 并在pullDownAction和pullUpAction中对其进行修改。
 */
public class TableStyle {
    //tableview cell样式

    String cellTemplateName;////string ui模板的文件名，用于加载ui模板

    //ui模板的最外层提供了requestInfo，解析从中拿到总的数据。而dataPath就是cell的数据在总数据中的路径。
    List<String> dataPath;//array: cell的数据路径 ["path","to","the","data"]
    int enablePullUpToLoadMore;
    int enablePullDownRefresh;
    String pullDownAction;
    String pullUpAction;
    String headView;//string, ui模板的文件名，用于加载ui模板
    String footView;//string, ui.... notice:当数据返回空时才会展示出来。下拉重新加载会隐藏。
    InsertViews[] insertViews;
    int scrollDisabled;
}
