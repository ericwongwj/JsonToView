package com.example.tn_ma_l30000048.myjsontest.parser;

import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/10.
 */

public class JsonViewManager {
    //TODO:需要做一些界面的网络请求的同步

    static Map<String, JsonRoot> mJsonViewMap;


//    static JsonRoot generateJsonRoot(String url){
//        return ;
//    }

    static JsonRoot getJsonRoot(String url) {
        return mJsonViewMap.get(url);
    }
}
