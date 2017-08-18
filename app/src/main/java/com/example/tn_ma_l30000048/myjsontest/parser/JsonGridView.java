package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.example.tn_ma_l30000048.myjsontest.MyDividerItemDecoration;
import com.example.tn_ma_l30000048.myjsontest.model.AbsoluteSize;
import com.example.tn_ma_l30000048.myjsontest.model.AtomicData;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;
import com.example.tn_ma_l30000048.myjsontest.view.Action;
import com.example.tn_ma_l30000048.myjsontest.view.MyRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by tn-ma-l30000048 on 17/8/1.
 */

public class JsonGridView {

    public static final String TAG = JsonGridView.class.getSimpleName() + " ";

    public static ViewWrapper build(JSONObject body, ViewGroupWrapper jsonRoot) {
        CollectionViewWrapper viewWrapper = new CollectionViewWrapper(jsonRoot.getContext());
        MyRecyclerView myRecyclerView = buildGridView(body, viewWrapper);
        viewWrapper.setJsonView(myRecyclerView);
        return viewWrapper;
    }

    public static MyRecyclerView buildGridView(JSONObject body, CollectionViewWrapper viewWrapper) {
        System.out.println("----buildViewGroup GridView----");
        MyRecyclerView gridView = new MyRecyclerView(viewWrapper.getContext());
        JsonViewUtils.setTagToWrapper(body, gridView, new ViewWrapper());
        setBaseStyle(JsonHelper.getStyles(body), gridView, viewWrapper);

        gridView.setLayoutManager(new GridLayoutManager(viewWrapper.getContext(), 2));

        gridView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        gridView.showSwipeRefresh();//在加载数据之后dismiss

        gridView.getRecyclerView().addItemDecoration(new MyDividerItemDecoration(viewWrapper.getContext(), LinearLayoutManager.VERTICAL));

        return gridView;
    }

    static void setBaseStyle(JSONObject json, MyRecyclerView rv, CollectionViewWrapper viewWrapper) {
        Iterator<String> keys = json.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("backgroundColor")) {
                    String bgColor = json.getString(key);
                    rv.setBackgroundColor(JasonHelper.parseColor(bgColor));
                } else if (key.equalsIgnoreCase("collectionStyle")) {
                    setCollectionStyle(json.getJSONObject(key), rv, viewWrapper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static void setCollectionStyle(JSONObject collectionStyle, MyRecyclerView rv, CollectionViewWrapper viewWrapper) {
        Context context = rv.getContext();
        Iterator<String> keys = collectionStyle.keys();
        try {
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equalsIgnoreCase("cellTemplateName")) {//这里有本地/网络IO
                    String cellName = collectionStyle.getString(key);
                    if (!TextUtils.isEmpty(cellName)) {//当创建cell模板时，需要设置containerHeight用于返回相应cell的高度??
                        viewWrapper.setCellJson(JsonHelper.findLocalJsonByName(context, cellName));
                    }
                } else if (key.equalsIgnoreCase("scrollDirection")) {
                    int direction = collectionStyle.getInt(key);

                } else if (key.equalsIgnoreCase("itemSize")) {
                    AbsoluteSize size = JsonUtils.decode(collectionStyle.getJSONObject(key).toString(), AbsoluteSize.class);
                    int colunmNum = (int) (1 / size.width.persentage);
                    rv.setLayoutManager(new GridLayoutManager(context, colunmNum));
                } else if (key.equalsIgnoreCase("minimumInteritemSpacing")) {
                    int value = collectionStyle.getInt(key);

                } else if (key.equalsIgnoreCase("minimumLineSpacing")) {
                    int value = collectionStyle.getInt(key);

                } else if (key.equalsIgnoreCase("dataPath")) {
                    JSONArray jsonArray = collectionStyle.getJSONArray(key);
                    AtomicData dataSource = JsonViewUtils.parseDataSource(jsonArray);
                    viewWrapper.setDataSource(dataSource);
                } else if (key.equalsIgnoreCase("enablePullUpToLoadMore")) {
                    if (collectionStyle.getInt(key) == 0)
                        rv.setLoadMoreAble(false);
                    else rv.setLoadMoreAble(true);
                } else if (key.equalsIgnoreCase("enablePullDownToRefresh")) {
                    if (collectionStyle.getInt(key) == 0)
                        rv.getSwipeRefreshLayout().setEnabled(false);
                    else rv.getSwipeRefreshLayout().setEnabled(true);
                } else if (key.equalsIgnoreCase("pullDownAction")) {//不执行太多逻辑
                    String jsCode = collectionStyle.getString(key);
                    //        mRecyclerView.setRefreshAction();
                    Action action = new Action() {
                        @Override
                        public void onAction() {

                        }
                    };
                    viewWrapper.setAction(action);
                } else if (key.equalsIgnoreCase("pullUpAction")) {
                    String jsCode = collectionStyle.getString(key);
                    //        mRecyclerView.setLoadMoreAction();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
