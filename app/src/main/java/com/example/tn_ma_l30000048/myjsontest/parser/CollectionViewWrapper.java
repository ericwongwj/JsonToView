package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;

import com.example.tn_ma_l30000048.myjsontest.view.Action;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;
import com.example.tn_ma_l30000048.myjsontest.view.RecyclerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/9.
 */

public class CollectionViewWrapper extends ViewWrapper {

    static final String TAG = CollectionViewWrapper.class.getSimpleName() + " ";
    static int num = 0;
    Action action;
    private RecyclerAdapter adapter;
    private JSONObject mHeadJson;
    private JSONObject mFootJson;
    private JSONObject mCellJson;
    private SparseArray<JSONObject> mInsertViewJson;
    private Map<String, Object> mRootDataMap;

    public CollectionViewWrapper(Context context) {
        super(context);
    }

    void initRecyclerView(List<Map<String, Object>> dataList) {
        System.out.println(TAG + "initRecyclerView");
        MyRecyclerView myRecyclerView = (MyRecyclerView) mJsonView;
        myRecyclerView.setBackgroundColor(0xCCCCCCCC);

        adapter = new RecyclerAdapter(myRecyclerView.getContext());
        if (mCellJson != null)
            adapter.setCell(mCellJson);//3种情形都需要测试
        if (mHeadJson != null)
            adapter.setHeader(mHeadJson);
        if (mFootJson != null)
            adapter.setFooter(mFootJson);
        if (mInsertViewJson != null)
            adapter.setInsertViews(mInsertViewJson);
        adapter.setDataMap(dataList);


        System.out.println(TAG + " view count:" + adapter.mViewCount);
        //第一次加载数据
        myRecyclerView.setAdapter(adapter);

        //需要现有adapter
        myRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        myRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
            }
        });
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

    public void getData(final boolean isRefresh) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyRecyclerView myRecyclerView = (MyRecyclerView) mJsonView;
                if (isRefresh) {
                    adapter.currentPage = 1;
                    adapter.clear();
                    adapter.addMapList(getVirtualData(), isRefresh);
                    myRecyclerView.dismissSwipeRefresh();
                    myRecyclerView.getRecyclerView().scrollToPosition(0);
                } else {
                    adapter.currentPage++;
                    adapter.addMapList(getVirtualData(), isRefresh);
                    if (adapter.currentPage >= 3) {
                        myRecyclerView.showNoMore();
                    }
                }
            }
        }, 1500);
    }

    List<Map<String,Object>> getVirtualData(){
        List<Map<String,Object>> data=new ArrayList<>();
        for(int i=0;i<8;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("nickName", "NICKNAME" + num++);
            map.put("avatar",Constants.ICON_URL1);
            data.add(map);
        }
        return data;
    }

    public void setAction(Action action) {
        this.action = action;
    }


    public void setRootDataMap(Map<String, Object> mRootDataMap) {
        this.mRootDataMap = mRootDataMap;
    }
}
