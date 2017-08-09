package com.example.tn_ma_l30000048.myjsontest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.parser.Constants;
import com.example.tn_ma_l30000048.myjsontest.utils.ViewUtils;

public class TestViewActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout;

    TextView tv;
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        tvTest=(TextView) findViewById(R.id.tv_test);
        layout=(LinearLayout)findViewById(R.id.contanier);
        tv=new TextView(this);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText("123");
        tv.setTextColor(Color.CYAN);
        tv.setTextSize(30);
        tv.setX(300);
        tv.setY(400);
        tv.setLines(1);
        tv.setPadding(30,20,30,20);
        tv.setBackgroundColor(0x66000000);
        LinearLayout.LayoutParams layoutParams=
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height=100;
        layoutParams.width=350;
        tv.setLayoutParams(layoutParams);
        layout.addView(tv);
        tv.setVisibility(View.VISIBLE);

        findViewById(R.id.btn_test).setOnClickListener(this);
        tv.setOnClickListener(this);

//        showDisPlayInOnCreate(tvTest);
        showDisPlayInOnCreate(tv);

        getWandH();


        ImageView imageView = (ImageView) findViewById(R.id.iv_test);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(Constants.BIG_URL1).asBitmap().into(imageView);
    }

    void showDisPlayInOnCreate(View v){
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        int height =v.getMeasuredHeight();
        int width =v.getMeasuredWidth();
        System.out.println("oncreate after measure  h:"+height+",w:"+width);//和最后的结果略有差别
    }

    void showDisplay(){
        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        System.out.println("display w:"+displayMetrics.widthPixels+" h:"+displayMetrics.heightPixels);
        int w=getWindowManager().getDefaultDisplay().getWidth();
        int h=getWindowManager().getDefaultDisplay().getHeight();
        System.out.println("display w:"+w+" h:"+h);
    }


    @Override
    public void onClick(View v) {
        if(v==tv){
            ViewUtils.showDisplayMetrics(tv);
        }
        else if(v==findViewById(R.id.btn_test)){
//            ViewUtils.showDisplayMetrics(findViewById(R.id.contanier));
//            ViewUtils.showDisplayMetrics(findViewById(R.id.tv_test));
//            ViewUtils.showDisplayMetrics(findViewById(R.id.btn_test));
            ViewUtils.showDisplayMetrics(tv);
        }
//        printLocationOnScreen();

        tv.setText("text");
        tv.setTextSize(15);

    }

    void getWandH(){
        ViewTreeObserver vto2 = tv.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tv.append("\n"+tv.getHeight()+","+tv.getWidth());
                System.out.println("vt:"+tv.getHeight()+","+tv.getWidth());
            }
        });
    }

    //获取的是在整个屏幕上的位置 包括顶栏通知栏
    void printLocationOnScreen(){
        int[] location = new int[2];
        tv.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        System.out.println("x:"+x+" y:"+y);
        tv.getLocationInWindow(location);
        System.out.println("x:"+location[0]+" y:"+location[1]);
    }
}
