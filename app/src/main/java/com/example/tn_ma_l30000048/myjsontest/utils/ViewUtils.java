package com.example.tn_ma_l30000048.myjsontest.utils;

import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tn-ma-l30000048 on 17/7/26.
 */

public class ViewUtils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

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

    /**
     * Generate a value suitable for use in setId.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
//            ID大于0x00FFFFFF的已经在xml中定义到了，容易发生冲突。
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
