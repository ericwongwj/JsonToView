package com.example.tn_ma_l30000048.myjsontest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;

public class TestRelativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_relative);

        LinearLayout root = (LinearLayout) findViewById(R.id.root_layout);

        testRelativeLayout(root);
    }


    void testRelativeLayout(LinearLayout root) {
        RelativeLayout container = new RelativeLayout(this);
        container.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        container.setBackgroundColor(Color.YELLOW);
        addCell(container);
        RelativeLayout container1 = new RelativeLayout(this);
        container1.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        container1.setBackgroundColor(Color.CYAN);
        addCell(container1);

        root.addView(container);
        root.addView(container1);
    }


    void addCell(RelativeLayout container) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher_round);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        layoutParams.topMargin = 15;
        layoutParams.leftMargin = 15;
        iv.setLayoutParams(layoutParams);
        iv.setId(1);

        TextView tv = new TextView(this);
        tv.setText("联系人");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        RelativeLayout.LayoutParams tvLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.leftMargin = 20;
        tvLayoutParams.addRule(RelativeLayout.ALIGN_TOP, 1);
        tvLayoutParams.topMargin = 20;
        tvLayoutParams.addRule(RelativeLayout.RIGHT_OF, 1);
        tv.setLayoutParams(tvLayoutParams);

        View line = new View(this);
        line.setBackgroundColor(Color.BLUE);
        RelativeLayout.LayoutParams lineLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        lineLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lineLayoutParams.bottomMargin = 13;
        line.setLayoutParams(lineLayoutParams);
        container.addView(tv);
        container.addView(iv);
        container.addView(line);
    }
}
