package com.example.tn_ma_l30000048.myjsontest.model;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class ImageStyles {
    int cornerRadius;
    int contentMode;
    AtomicData imageSource;

    @Override
    public String toString() {
        return cornerRadius+" "+contentMode+" "+imageSource.data;
    }
}
