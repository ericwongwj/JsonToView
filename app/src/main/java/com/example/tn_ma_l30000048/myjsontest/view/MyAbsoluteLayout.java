package com.example.tn_ma_l30000048.myjsontest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by tn-ma-l30000048 on 17/8/15.
 */

public class MyAbsoluteLayout extends ViewGroup {

    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);

    public MyAbsoluteLayout(Context context) {
        super(context);
    }

    public MyAbsoluteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyAbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
