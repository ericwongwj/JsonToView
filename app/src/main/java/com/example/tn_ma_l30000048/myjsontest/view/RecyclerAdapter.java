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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private static final int HEADER_TYPE = 111;
    private static final int FOOTER_TYPE = 222;
    private static final int STATUS_TYPE = 333;
    private static final int INSERT_TYPE = 444;

    private static final String TAG = "RecyclerAdapter";
    public boolean isShowNoMore = false;   //停止加载
    public boolean loadMoreAble = false;   //是否可加载更多
    public TextView mNoMoreView;
    protected Action mLoadMoreAction;
    protected View mStatusView;
    protected LinearLayout mLoadMoreView;
    int mViewCount = 0;
    private boolean hasHeader = false;
    private boolean hasFooter = false;
    private List<T> mData = new ArrayList<>();
    private JSONObject mHeaderJson;
    private JSONObject mFooterJson;
    private JSONObject mCellJson;
    private List<JSONObject> mInsertJsons;
    private SparseArray<JSONObject> mInsertRows;
    private int rowsIndex = 0;

    private Context mContext;


    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(Context context) {
        mContext = context;
        initStatusView();
    }

    public RecyclerAdapter(Context context, List<T> data) {
        mContext = context;
        initStatusView();
        this.mData = data;
        mViewCount += data.size();
        notifyDataSetChanged();
    }

    public void initStatusView() {//包括正在加载和没有更多了
        mStatusView = LayoutInflater.from(mContext).inflate(R.layout.view_last_status, null);
        mStatusView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLoadMoreView = (LinearLayout) mStatusView.findViewById(R.id.load_more_view);
        mNoMoreView = (TextView) mStatusView.findViewById(R.id.no_more_view);
        mViewCount++;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            JsonRoot header = new JsonRoot(mHeaderJson, mContext, parent.getWidth(), parent.getHeight());
            return new BaseViewHolder<>(header.getJsonView());
        } else if (viewType == FOOTER_TYPE) {
            JsonRoot footer = new JsonRoot(mFooterJson, mContext, parent.getWidth(), parent.getHeight());
            return new BaseViewHolder<>(footer.getJsonView());
        } else if (viewType == STATUS_TYPE) {
            return new BaseViewHolder<>(mStatusView);
        } else if (viewType == INSERT_TYPE) {
            System.out.println("create insert holder");
            JsonRoot insertView = new JsonRoot(mInsertJsons.get(rowsIndex++), mContext, parent.getWidth(), parent.getHeight());
            return new BaseViewHolder<>(insertView.getJsonView());
        } else {
            JsonRoot cell = new JsonRoot(mCellJson, mContext, parent.getWidth(), parent.getHeight());
            return new BaseViewHolder<>(cell.getJsonView());
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        System.out.println("mData.size()" + mData.size() + " pos=" + position);
        //重点在这里 还可能要处理一些网络请求的问题
        position = position % 8;
        if (position == 0) {
            if (mViewCount == 1 || hasHeader)
                return;
            else holder.setData(mData.get(0));
        } else if (!hasHeader && !hasFooter && position < mData.size()) { //没有Header和Footer
            holder.setData(mData.get(position));
        } else if (hasHeader && !hasFooter && position > 0 && position < mViewCount - 1) { //有Header没有Footer
            holder.setData(mData.get(position - 1));
        } else if (!hasHeader && position < mViewCount - 2) { //没有Header，有Footer
            holder.setData(mData.get(position));
        } else if (position > 0 && position < mViewCount - 2) { //都有
            holder.setData(mData.get(position - 1));
        }

        // 最后一个可见的 item 时 加载更多。
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
        if (mInsertRows.get(position) != null) return INSERT_TYPE;
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
        if (mData == null) return;
        mData.clear();
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
        mViewCount++;
    }

    public void setFooter(JSONObject footer) {
        hasFooter = true;
        mFooterJson = footer;
        mViewCount++;
    }

    public void setInsertViews(List<JSONObject> insertViews) {
        mInsertRows = new SparseArray<>();
        mInsertJsons = insertViews;
        for (JSONObject insertView : insertViews) {
            int row = 0;
            try {
                row = insertView.getInt("row");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mInsertRows.put(row, insertView);//这里put进去的应该是得到的insertView界面的json

        }
        mViewCount += insertViews.size();
    }


    public void addAll(T[] data) {
        addAll(Arrays.asList(data));
    }

    public void addAll(List<T> data) {
        int size = data.size();
        if (!isShowNoMore && size > 0) {
            mData.addAll(data);
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


}
