package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/25.
 */

public class CollectionStyle {
    String cellTemplateName;//string ui模板的文件名，用于加载ui模板
    int scrollDirection;//int: 0:vertical; 1:horizontal
    List<String> dataPath;//array: cell的数据路径 ["path","to","the","data"]
    int enablePullUpToLoadMore;//int 0 or 1
    int enablePullDownToRefresh;
    String pullDownAction; //string js code "{{}}",会在请求数据之前调用
    String pullUpAction;
    AbsoluteSize itemSize;
    int minimumInteritemSpacing;
    int minimumLineSpacing;
    int scrollDisabled;//int 0:默认可以滑动；1：不能滑动
}
