package com.example.tn_ma_l30000048.myjsontest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonHelper;

import org.json.JSONObject;

public class TestRelativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_relative);

        LinearLayout root = (LinearLayout) findViewById(R.id.root_layout);

        testRelativeLayout(root);
//        testRelativeJson(root);
    }


    void testRelativeJson(LinearLayout root) {
        JSONObject json = JsonHelper.readLocalUIJson(this, "Mytest1.json");

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

        RelativeLayout container2 = new RelativeLayout(this);
        container2.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        container2.setBackgroundColor(Color.MAGENTA);
        Button btn = new Button(this);
        btn.setX(250);
        btn.setY(200);
        btn.setText("btn");
        container2.addView(btn);
        // root.addView(container2);

        RelativeLayout container3 = new RelativeLayout(this);
        container3.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 350));
//        container3.setBackgroundColor(Color.MAGENTA);
        final Button btn1 = new Button(this);
        btn1.setText("btn1");
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        btn1.setLayoutParams(lp1);
        btn1.setId(ViewGroup.generateViewId());

        Button btn2 = new Button(this);
        btn2.setText("btn2");
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_LEFT, btn1.getId());
        lp2.rightMargin = 200;
        lp2.topMargin = 130;
        lp2.addRule(RelativeLayout.ALIGN_TOP, btn1.getId());
        btn2.setLayoutParams(lp2);

        final Button btn3 = new Button(this);
        btn3.setText("btn3");
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.LEFT_OF, btn1.getId());
        lp3.topMargin = 100;
        lp3.rightMargin = 150;
        lp3.addRule(RelativeLayout.BELOW, btn1.getId());
        btn3.setLayoutParams(lp3);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp3.addRule(RelativeLayout.LEFT_OF, btn1.getId());
                lp3.topMargin = 150;
                lp3.rightMargin = 300;
                lp3.addRule(RelativeLayout.BELOW, btn1.getId());
                btn3.setLayoutParams(lp3);
            }
        });

        container3.addView(btn1);
        container3.addView(btn2);
        container3.addView(btn3);
        root.addView(container3);
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
