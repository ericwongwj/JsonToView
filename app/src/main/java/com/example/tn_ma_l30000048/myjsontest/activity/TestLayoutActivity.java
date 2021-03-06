package com.example.tn_ma_l30000048.myjsontest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.model.Model;
import com.example.tn_ma_l30000048.myjsontest.utils.DensityUtils;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;
import com.example.tn_ma_l30000048.myjsontest.utils.ScreenUtils;

public class TestLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);

        LinearLayout root=(LinearLayout) findViewById(R.id.root_layout);

//        testRelativeLayout(root);

        testFrameLayout(root);
    }

    void testFrameLayout(LinearLayout root){
        FrameLayout container=new FrameLayout(this);
        container.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        container.setBackgroundColor(Color.YELLOW);

        addCell(container);
        root.addView(container);
    }

    void testRelativeLayout(LinearLayout root){
        RelativeLayout container=new RelativeLayout(this);
        container.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
        container.setBackgroundColor(Color.YELLOW);
        addCell(container);
        RelativeLayout container1=new RelativeLayout(this);
        container1.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
        container1.setBackgroundColor(Color.CYAN);
        addCell(container1);

        root.addView(container);
        root.addView(container1);
    }

    //:x = <基准>*<百分比> + <偏移量>
    void addCell(FrameLayout container){
        ImageView iv=new ImageView(this);
        iv.setImageResource(R.drawable.icon);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(150,150);
        float x1=DensityUtils.dp2px(this,15);
        float y1=DensityUtils.dp2px(this,8);
        int w=(int)DensityUtils.dp2px(this,40);
        int h=(int)DensityUtils.dp2px(this,40);
        iv.setX(x1);iv.setY(y1);
        layoutParams.width=w;layoutParams.height=h;
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView tv=new TextView(this);
        tv.setText("联系人");
        tv.setTextColor(JasonHelper.parse_color("#051b28"));
        tv.setTextSize(17);
        tv.setX(DensityUtils.dp2px(this,70));
        tv.setY(DensityUtils.dp2px(this,12));
        RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(150,150);
        layoutParams1.width=(int)DensityUtils.dp2px(this,-70)+ ScreenUtils.getScreenWidth(this);
        layoutParams1.height=31+ScreenUtils.getScreenWidth(this);

        container.addView(tv);
        container.addView(iv);
    }

    void addCell(RelativeLayout container){
        ImageView iv=new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher_round);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(150,150);
        layoutParams.topMargin=15;
        layoutParams.leftMargin=15;
        iv.setLayoutParams(layoutParams);
        iv.setId(1);

        TextView tv=new TextView(this);
        tv.setText("联系人");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        RelativeLayout.LayoutParams tvLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.leftMargin=20;
        tvLayoutParams.addRule(RelativeLayout.ALIGN_TOP,1);
        tvLayoutParams.topMargin=20;
        tvLayoutParams.addRule(RelativeLayout.RIGHT_OF,1);
        tv.setLayoutParams(tvLayoutParams);

        View line=new View(this);
        line.setBackgroundColor(Color.BLUE);
        RelativeLayout.LayoutParams lineLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        lineLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lineLayoutParams.bottomMargin=13;
        line.setLayoutParams(lineLayoutParams);
        container.addView(tv);
        container.addView(iv);
        container.addView(line);
    }

    void testLinearLayout(){
        LinearLayout container=new LinearLayout(this);
        container.setBackgroundColor(0xDDDDDDDD);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height=250;
        layoutParams.topMargin=10;
        layoutParams.bottomMargin=10;
        layoutParams.leftMargin=10;
        layoutParams.rightMargin=10;

        TextView tv=new TextView(this);
        tv.setText("联系人");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(18);
        tv.setBackgroundColor(getColor(R.color.yellow));

        ImageView iv=new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher_round);

        View line=new View(this);
        line.setBackgroundColor(Color.GRAY);
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        container.addView(tv);
        container.addView(iv);
        container.addView(line);
    }

    void testContactCell(){
        String s1=JasonHelper.readLocalJsonToString("jsons://layout/TNChatContactList.json",this);
        Model model= JsonUtils.decode(s1,Model.class);
        System.out.println(model);
    }
}
