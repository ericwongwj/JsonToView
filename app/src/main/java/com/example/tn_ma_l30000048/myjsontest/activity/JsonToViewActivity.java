package com.example.tn_ma_l30000048.myjsontest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonViewRoot;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonToViewActivity extends FragmentActivity implements View.OnClickListener {

    FrameLayout root;
    JSONObject jsonObject;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_to_view);
        getJson();
        root = (FrameLayout) findViewById(R.id.root_layout);
        btn = (Button) findViewById(R.id.btn_render);
        btn.setOnClickListener(this);
        ViewGroup.generateViewId();
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
        JsonViewRoot myRoot = new JsonViewRoot(jsonObject, JsonToViewActivity.this, root.getWidth(), root.getHeight());
        if (myRoot.getJsonView() != null) {
            root.addView(myRoot.getJsonView());
            btn.setVisibility(View.GONE);
        }
    }
}
