package com.example.tn_ma_l30000048.myjsontest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.MyDividerItemDecoration;
import com.example.tn_ma_l30000048.myjsontest.R;

import java.util.ArrayList;
import java.util.List;

public class TestGridActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_grid);

        recyclerView = (RecyclerView) findViewById(R.id.rv_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Adapter adapter = new Adapter(getData());
        MyDividerItemDecoration decoration = new MyDividerItemDecoration(this, MyDividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }

    List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("demo " + i);
        }
        return list;
    }

    class Adapter extends RecyclerView.Adapter<Vh> {

        List<String> data;

        Adapter(List<String> list) {
            data = list;
        }

        @Override
        public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(TestGridActivity.this).inflate(R.layout.rv_item, parent, false);
            return new Vh(v);
        }

        @Override
        public void onBindViewHolder(Vh holder, int position) {
            holder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


    }

    class Vh extends RecyclerView.ViewHolder {
        TextView tv;

        public Vh(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

}

