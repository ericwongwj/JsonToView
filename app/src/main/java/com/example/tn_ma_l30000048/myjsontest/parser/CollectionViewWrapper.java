package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.util.SparseArray;

import com.example.tn_ma_l30000048.myjsontest.view.Action;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;
import com.example.tn_ma_l30000048.myjsontest.view.RecyclerAdapter;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/9.
 */

public class CollectionViewWrapper extends ViewWrapper {

    static final String TAG = CollectionViewWrapper.class.getSimpleName() + " ";
    private RecyclerAdapter adapter;
    private JSONObject mHeadJson;
    private JSONObject mFootJson;
    private JSONObject mCellJson;
    private SparseArray<JSONObject> mInsertViewJson;

    public CollectionViewWrapper(Context context) {
        super(context);
    }

    void initRecyclerView(List<Map<String, Object>> dataList) {
        System.out.println(TAG + "initRecyclerView");
        MyRecyclerView myRecyclerView = (MyRecyclerView) mJsonView;
        myRecyclerView.setBackgroundColor(0xAAAAAAAA);
        myRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {

            }
        });

        adapter = new RecyclerAdapter(myRecyclerView.getContext());
        adapter.setCell(mCellJson);
        //adapter.setHeader(mHeadJson);
        //adapter.setFooter(mFootJson);
        adapter.setmDataMap(dataList);


        //第一次加载数据
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.dismissSwipeRefresh();
    }


    public void setCellJson(JSONObject cellJson) {
        mCellJson = cellJson;
    }

    public void setHeadJson(JSONObject headJson) {
        mHeadJson = headJson;
    }

    public void setFootJson(JSONObject footJson) {
        mFootJson = footJson;
    }

    public void setInsertViewJson(SparseArray<JSONObject> insertViewJson) {
        this.mInsertViewJson = insertViewJson;
    }

//    public void getData(final boolean isRefresh) {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isRefresh) {
//                    page = 1;
//                    mAdapter.clear();
//                    mAdapter.addAll(getVirtualData());
//                    mRecyclerView.dismissSwipeRefresh();
//                    mRecyclerView.getRecyclerView().scrollToPosition(0);
//                } else {
//                    mAdapter.addAll(getVirtualData());
//                    if (page >= 3) {
//                        mRecyclerView.showNoMore();
//                    }
//                }
//            }
//        }, 1500);
//    }
}
