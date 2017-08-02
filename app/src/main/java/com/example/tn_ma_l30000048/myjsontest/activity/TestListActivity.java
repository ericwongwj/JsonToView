package com.example.tn_ma_l30000048.myjsontest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.tn_ma_l30000048.myjsontest.MyAdapter;
import com.example.tn_ma_l30000048.myjsontest.MyDividerItemDecoration;
import com.example.tn_ma_l30000048.myjsontest.MyDynamicAdapter;
import com.example.tn_ma_l30000048.myjsontest.R;

import java.util.ArrayList;


public class TestListActivity extends AppCompatActivity implements View.OnClickListener{

    MyDynamicAdapter mDynamicAdapter;

    private RecyclerView mRecyclerView;

    private MyAdapter mAdapter;


    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.tv_delete).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_test);
        if (getIntent().getStringExtra("version") != null) {
            mRecyclerView.setVisibility(View.GONE);
        } else {
            initDynamic();
        }

    }


    void initDynamic(){
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mDynamicAdapter=new MyDynamicAdapter(getData(),this);
        mRecyclerView.setAdapter(mDynamicAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDynamicAdapter.setOnItemClickListener(new MyDynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(TestListActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(TestListActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initStatic(){
        mAdapter = new MyAdapter(getData());
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(TestListActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(TestListActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " 联系人";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }

    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_add) {
            mAdapter.addNewItem();
            // 由于Adapter内部是直接在首个Item位置做增加操作，增加完毕后列表移动到首个Item位置
            mLayoutManager.scrollToPosition(0);
        } else if(id == R.id.tv_delete){
            mAdapter.deleteItem();
            // 由于Adapter内部是直接在首个Item位置做删除操作，删除完毕后列表移动到首个Item位置
            mLayoutManager.scrollToPosition(0);
        }
    }
}
