package com.example.tn_ma_l30000048.myjsontest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;

public class ShowJsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String json=intent.getStringExtra("json");

        TextView textView=new TextView(this);
        textView.setVerticalScrollBarEnabled(true);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        setContentView(textView);
        textView.setTextSize(10);
        textView.setText(json);

    }
}
