package com.example.tn_ma_l30000048.myjsontest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by tn-ma-l30000048 on 17/8/15.
 */

public class MyGenericLayout extends ViewGroup {
    public MyGenericLayout(Context context) {
        super(context);
    }

    public MyGenericLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGenericLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyGenericLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
