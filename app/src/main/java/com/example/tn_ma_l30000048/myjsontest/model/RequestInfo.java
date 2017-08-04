package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.HashMap;

/**
 * Created by tn-ma-l30000048 on 17/8/4.
 */

public class RequestInfo {
    public String baseUrl;
    public String path;
    public HashMap<String, String> parameters;
    public String parameterString;

    @Override
    public String toString() {
        return baseUrl + " " + path + " " + parameters.get(parameters.keySet().iterator().next());
    }

    //    static class Parameters {
//        String sessionId;
//        String page;
//        String count;
//    }
}
