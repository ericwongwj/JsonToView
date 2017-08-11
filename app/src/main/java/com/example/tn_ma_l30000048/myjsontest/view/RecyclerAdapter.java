package com.example.tn_ma_l30000048.myjsontest.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonRoot;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int HEADER_TYPE = 111;
    private static final int FOOTER_TYPE = 222;
    private static final int STATUS_TYPE = 333;
    private static final int INSERT_TYPE = 444;

    private static final String TAG = "RecyclerAdapter ";
    public boolean isShowNoMore = false;   //停止加载
    public boolean loadMoreAble = false;   //是否可加载更多
    public TextView mNoMoreView;
    public int mViewCount = 0;
    public int currentPage = 0;
    protected Action mLoadMoreAction;
    protected View mStatusView;
    protected LinearLayout mLoadMoreView;
    int pageNum = 5;//一页十个数据
    private boolean hasHeader = false;
    private boolean hasFooter = false;

    private Map<String, Object> rootDataMap;
    private List<Map<String, Object>> mDataMapList = new ArrayList<>();

    private JSONObject mHeaderJson;
    private JSONObject mFooterJson;
    private JSONObject mCellJson;
    private SparseArray<JSONObject> mInsertJsons;
    private int insertRowsIndex = 0;

    private Context mContext;

    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(Context context) {
        mContext = context;
        initStatusView();
    }

    public void initStatusView() {//包括正在加载和没有更多了
        mStatusView = LayoutInflater.from(mContext).inflate(R.layout.view_last_status, null);
        mStatusView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLoadMoreView = (LinearLayout) mStatusView.findViewById(R.id.load_more_view);
        mNoMoreView = (TextView) mStatusView.findViewById(R.id.no_more_view);
        mViewCount++;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println(TAG + "onCreateViewHolder " + viewType);
        if (viewType == HEADER_TYPE) {
            JsonRoot header = new JsonRoot(mHeaderJson, mContext, parent.getWidth(), parent.getHeight(), true);
            return new BaseViewHolder(header);
        }
        if (viewType == FOOTER_TYPE) {
            JsonRoot footer = new JsonRoot(mFooterJson, mContext, parent.getWidth(), parent.getHeight(), true);
            return new BaseViewHolder(footer);
        }
        if (viewType == STATUS_TYPE) {
            return new BaseViewHolder(mStatusView);
        }
        if (viewType == INSERT_TYPE) {
            //这里有网络请求的逻辑
            System.out.println("insertViewType");
            JsonRoot insertView = new JsonRoot(mInsertJsons.get(insertRowsIndex++), mContext, parent.getWidth(), parent.getHeight());
            return new BaseViewHolder(insertView);
        } else {
            JsonRoot cell = new JsonRoot(mCellJson, mContext, parent.getWidth(), parent.getHeight(), true);
            return new BaseViewHolder(cell);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        System.out.println(TAG + " onBindViewHolder  pos=" + position);

        if (position == 0) {//header的位置
            if (mViewCount == 1)  // 最先加载 mStatusView 时不需要绑定数据
                return;
            if (!hasHeader && !mDataMapList.isEmpty()) {
                holder.setCellData(mDataMapList.get(0));
            } else {
                holder.setUniqueData(rootDataMap);
            }
        } else if (!hasHeader && !hasFooter && position < mDataMapList.size()) { //没有Header和Footer
            holder.setCellData(mDataMapList.get(position));
        } else if (hasHeader && !hasFooter && position > 0 && position < mViewCount - 1) { //有Header没有Footer
            holder.setCellData(mDataMapList.get(position - 1));
        } else if (!hasHeader && position < mViewCount - 2) { //没有Header，有Footer
            holder.setCellData(mDataMapList.get(position));
        } else if (position > 0 && position < mViewCount - 2) { //都有
            holder.setCellData(mDataMapList.get(position - 1));
        }


//        onItemClickListener.onItemClick();
//        holder.mCellRoot.getJsonView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

//         最后一个可见的 item 时 加载更多。
        int positionEnd;
        if ((hasHeader && hasFooter) || (!hasHeader && hasFooter)) {
            positionEnd = mViewCount - 3;
        } else {
            positionEnd = mViewCount - 2;
        }
        if (loadMoreAble && !isShowNoMore && position == positionEnd) {
            mLoadMoreView.setVisibility(View.VISIBLE);
            if (mLoadMoreAction != null) {
                mLoadMoreAction.onAction();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader && position == 0) return HEADER_TYPE;
        if (hasFooter && position == mViewCount - 2) return FOOTER_TYPE;
        if (position == mViewCount - 1) return STATUS_TYPE;
        if (mInsertJsons != null && mInsertJsons.get(position) != null) return INSERT_TYPE;
        return super.getItemViewType(position);//普通的cell目前设为统一的
    }

    @Override
    public int getItemCount() {
        return mViewCount;
    }

    public void showNoMore() {
        isShowNoMore = true;
        mLoadMoreView.post(new Runnable() {
            @Override
            public void run() {
                mLoadMoreView.setVisibility(View.GONE);
                mNoMoreView.setVisibility(View.VISIBLE);
            }
        });
    }


    public void setLoadMoreAction(Action action) {
        mLoadMoreAction = action;
    }

    public void clear() {
//        if (mData == null) return;
//        mData.clear();
        mViewCount = 1;
        if (hasHeader)
            mViewCount++;
        if (hasFooter)
            mViewCount++;
        isShowNoMore = false;
        mLoadMoreView.setVisibility(View.GONE);
        mNoMoreView.setVisibility(View.GONE);
        notifyDataSetChanged();
    }

    public void setHeader(JSONObject header) {
        hasHeader = true;
        mHeaderJson = header;
        mViewCount++;
    }

    public void setCell(JSONObject cell) {
        mCellJson = cell;
    }

    public void setFooter(JSONObject footer) {
        hasFooter = true;
        mFooterJson = footer;
        mViewCount++;
    }

    public void setInsertViews(SparseArray<JSONObject> insertViews) {
        mInsertJsons = insertViews;
        mViewCount += insertViews.size();
    }

    //只在第一次初始化的时候调用
    public void setDataMap(List<Map<String, Object>> mDataMap) {
        this.mDataMapList = mDataMap;
        mViewCount += mDataMap.size();
    }


    public void addAll(List<Object> data) {
        int size = data.size();
        if (!isShowNoMore && size > 0) {
            //mData.addAll(data);
            int positionStart;
            if (hasFooter) {
                positionStart = mViewCount - 2;
            } else {
                positionStart = mViewCount - 1;
            }
            mViewCount += size;
            notifyItemRangeInserted(positionStart, size);
        }
    }

    public void addMapList(List<Map<String, Object>> data, boolean isRefresh) {
        int size = data.size();
        if (!isShowNoMore && size > 0) {
            if (isRefresh)
                mDataMapList.addAll(0, data);
            else mDataMapList.addAll(data);
            int positionStart;
            if (hasFooter) {
                positionStart = mViewCount - 2;
            } else {
                positionStart = mViewCount - 1;
            }
            mViewCount += size;
            notifyItemRangeInserted(positionStart, size);
        }
    }

    public void setDataToView(List<Map<String, Object>> dataList) {
        //重新bind
        notifyDataSetChanged();
    }


    interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


}
