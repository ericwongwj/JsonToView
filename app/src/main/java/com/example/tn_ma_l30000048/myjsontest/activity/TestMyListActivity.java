package com.example.tn_ma_l30000048.myjsontest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.view.Action;
import com.example.tn_ma_l30000048.myjsontest.view.Bean;
import com.example.tn_ma_l30000048.myjsontest.view.ContactListAdapter;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;

public class TestMyListActivity extends AppCompatActivity {

    private MyRecyclerView mRecyclerView;
    private ContactListAdapter mAdapter;
    private Handler mHandler = new Handler();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my_list);

        mAdapter = new ContactListAdapter(this);

        //添加Header
        final TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setText("Tuniu App");
        mAdapter.setHeader(textView);
        final TextView footer = new TextView(this);
        footer.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        footer.setTextSize(16);
        footer.setGravity(Gravity.CENTER);
        footer.setText("-- Footer --");
        mAdapter.setFooter(footer);

        mRecyclerView = (MyRecyclerView) findViewById(R.id.enhanced_recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
                page++;
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

    }

    public void getData(final boolean isRefresh) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
                    mAdapter.addAll(getVirtualData());
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                } else {
                    mAdapter.addAll(getVirtualData());
                    if (page >= 3) {
                        mRecyclerView.showNoMore();
                    }
                }
            }
        }, 1500);
    }

    public Bean[] getVirtualData() {
        return new Bean[]{
                new Bean("Demo", R.drawable.pic1),
                new Bean("Demo", R.drawable.pic2),
                new Bean("Demo", R.drawable.pic3),
                new Bean("Demo", R.drawable.pic1),
                new Bean("Demo", R.drawable.pic2),
                new Bean("Demo", R.drawable.pic3),
                new Bean("Demo", R.drawable.pic1),
                new Bean("Demo", R.drawable.pic2),
        };
    }
}
