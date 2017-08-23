package com.example.tn_ma_l30000048.myjsontest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonRoot;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 这就是一个通用的Activity 可以往里面塞任何用json界面描述的东西
 */
public class JsonToViewActivity extends FragmentActivity implements View.OnClickListener {

    RelativeLayout root;
    JSONObject jsonObject;
    Button btn;

    JsonRoot jsonRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_to_view);
        requestJson();
        root = (RelativeLayout) findViewById(R.id.root_layout);
        btn = (Button) findViewById(R.id.btn_render);
        btn.setOnClickListener(this);
        btn.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onClick(null);
            }
        }, 350);
    }

    void requestJson() {
        Intent intent=getIntent();
        String str=intent.getStringExtra("json");
        String title=intent.getStringExtra("title");
        setTitle(title);
        try {
            jsonObject= new JSONObject(str);
            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        jsonRoot = new JsonRoot(jsonObject, JsonToViewActivity.this, root.getWidth(), root.getHeight());
        if (jsonRoot.getJsonView() != null) {
            View jsonView = jsonRoot.getJsonView();
            root.addView(jsonView);
            btn.setVisibility(View.GONE);
        } else {
            System.out.println(getClass().getSimpleName() + " Json Root View is null");
        }
    }

    //sessionId:3872cc4c730130c1031b15a71a1e9300_ count:10 page:1


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
