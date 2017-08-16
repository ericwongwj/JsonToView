package com.example.tn_ma_l30000048.myjsontest;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by tn-ma-l30000048 on 17/8/16.
 */

public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        System.out.println("application oncreate");
        Stetho.initializeWithDefaults(this);
    }

}
