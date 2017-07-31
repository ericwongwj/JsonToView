package com.example.tn_ma_l30000048.myjsontest.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.JsonUtils;
import com.example.tn_ma_l30000048.myjsontest.MyRoot;
import com.example.tn_ma_l30000048.myjsontest.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonToViewActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout root;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_to_view);
        getJson();
        root = (FrameLayout) findViewById(R.id.root_layout);
        findViewById(R.id.btn_render).setOnClickListener(this);

    }

    void getJson(){
        Intent intent=getIntent();
        String str=intent.getStringExtra("json");
        String title=intent.getStringExtra("title");
        setTitle(title);
        try {
            jsonObject= new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        MyRoot myRoot=new MyRoot(jsonObject, JsonToViewActivity.this, root.getWidth(),root.getHeight());
        if(myRoot.getJsonView()!=null)
            root.addView(myRoot.getJsonView());
    }
}
