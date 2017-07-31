package com.example.tn_ma_l30000048.myjsontest.utils;

import android.view.View;

/**
 * Created by tn-ma-l30000048 on 17/7/26.
 */

public class ViewUtils {

    public static void showDisplayMetrics(View view){
        System.out.println("===================");
        showPosition(view);
        showSize(view);
        System.out.println("===================");
    }

    public static void showSize(View view){
        System.out.println("w:"+view.getWidth()+" h:"+view.getHeight());
    }

    public static void showPosition(View view){
        System.out.println("x:"+view.getX()+" y:"+view.getY());
    }

}
