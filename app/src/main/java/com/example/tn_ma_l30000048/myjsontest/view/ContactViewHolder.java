package com.example.tn_ma_l30000048.myjsontest.view;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class ContactViewHolder extends BaseViewHolder<Bean> {

    private ImageView imageView;
    private TextView textView;

    public ContactViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onItemViewClick(Bean object) {
        super.onItemViewClick(object);
        Log.i("CardRecordHolder", "onItemViewClick");
    }
}
