package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/10.
 * 这里需要做的是后台请求界面数据  如果界面数据还嵌有其他界面继续请求
 * 扫描界面的时候进行数据请求
 *
 * 1.根据Activity中给定的界面url请求得到所有的json界面文件
 * 2.根据得到的所有界面文件构建view的属性，层次，layout，即可加载到界面上
 * 3.数据的加载应该在哪里？ 连续调用requestInfo该怎么办 是直接连续调用来构建datamap吗
 *
 */

public class JsonViewManager {
    static Map<String, JSONObject> mJsonViewMap;

    Thread thread;

    static void fetchUI(Context context, String url) {
        JSONObject jsonObject = JsonHelper.readLocalUIJson(context, "url");//后面应该变为异步网络操作

        JSONObject cellJson = JsonHelper.readLocalUIJson(context, "TNChatContactCell");
        JSONObject headJson = JsonHelper.readLocalUIJson(context, "TNChatContactHead");
        JSONObject footJson = JsonHelper.readLocalUIJson(context, "TNChatContactFoot");
    }


}
