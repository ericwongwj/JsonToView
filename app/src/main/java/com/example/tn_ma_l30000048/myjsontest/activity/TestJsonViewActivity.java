package com.example.tn_ma_l30000048.myjsontest.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.MyRoot;
import com.example.tn_ma_l30000048.myjsontest.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TestJsonViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json_view);

        LinearLayout root=(LinearLayout)findViewById(R.id.root_layout);
        final RelativeLayout container=(RelativeLayout)findViewById(R.id.contanier);
        container.setBackgroundColor(Color.CYAN);

        JSONObject jsonObject=generateTextView();
//        TextView tv1=(TextView) ViewFactory.build(jsonObject,this,container.getWidth(),container.getHeight());
//        container.addView(tv1);
//
        JSONObject jsonObject1=generateImageView();
//        ImageView iv1=(ImageView)ViewFactory.build(jsonObject1,this,container.getWidth(),container.getHeight());
//        container.addView(iv1);

        JSONObject jsonObject2=generateListView();
//        RecyclerView rv= (RecyclerView) ViewFactory.build(jsonObject2,this,container.getWidth(),container.getHeight());

//        container.addView(rv);

        final JSONObject loadFailCard=testLoadFailCard();


        new Handler().postDelayed(new Runnable(){
            public void run() {
                System.out.println("w="+container.getWidth()+"h="+container.getHeight());
                MyRoot myRoot=new MyRoot(loadFailCard, TestJsonViewActivity.this, container.getWidth(),container.getHeight());
                container.addView(myRoot.getJsonView());

            }
        }, 1000);

    }


    JSONObject generateTextView(){
        String s1= JasonHelper.readLocalJsonToString("jsons://view/textLabel.json",this);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    JSONObject generateImageView(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/image.json",this);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    JSONObject generateListView(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/tableview.json",this);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    JSONObject testLoadFailCard(){
        String s1=JasonHelper.readLocalJsonToString("jsons://layout/TNChatLoadFailCard.json",this);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    JSONObject testLoadChatCell(){
        String s1=JasonHelper.readLocalJsonToString("jsons://layout/TNChatContactCell.json",this);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    void addCell(ViewGroup container){
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
}
