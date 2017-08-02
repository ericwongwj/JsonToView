package com.example.tn_ma_l30000048.myjsontest.view;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;


/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class ContactViewHolder extends BaseViewHolder<Bean> {

    private ImageView imageView;
    private TextView textView;

    public ContactViewHolder(ViewGroup parent) {
        super(parent, R.layout.view_contact_item);
    }

    @Override
    public void setData(Bean data) {
        super.setData(data);//setClickListener
        textView.setText(data.text);
        imageView.setImageResource(data.picId);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        imageView = findViewById(R.id.iv_contact);
        textView = findViewById(R.id.tv_contact);
    }

    @Override
    public void onItemViewClick(Bean object) {
        super.onItemViewClick(object);
        Log.i("CardRecordHolder", "onItemViewClick");
    }
}
