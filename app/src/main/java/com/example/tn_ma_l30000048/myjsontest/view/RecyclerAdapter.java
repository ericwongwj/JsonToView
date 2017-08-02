package com.example.tn_ma_l30000048.myjsontest.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tn_ma_l30000048.myjsontest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    public static final int HEADER_TYPE = 111;
    public static final int FOOTER_TYPE = 222;
    public static final int STATUS_TYPE = 333;
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
    private View headerView;
    private View footerView;
    private ViewGroup cellView;//暂时定义为一个ViewGroup且cell相同 cell未必相同
    private Context mContext;

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


    public void initStatusView() {
        mStatusView = LayoutInflater.from(mContext).inflate(R.layout.view_last_status, null);
        mStatusView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mLoadMoreView = (LinearLayout) mStatusView.findViewById(R.id.load_more_view);
        mNoMoreView = (TextView) mStatusView.findViewById(R.id.no_more_view);
        mViewCount++;
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewType由getItemViewType产生
        if (viewType == HEADER_TYPE) {
            return new BaseViewHolder<>(headerView);
        } else if (viewType == FOOTER_TYPE) {
            return new BaseViewHolder<>(footerView);
        } else if (viewType == STATUS_TYPE) {
            return new BaseViewHolder<>(mStatusView);
        } else
            return onCreateBaseViewHolder(parent, viewType);
//        return new BaseViewHolder<>(cellView);
    }

    protected abstract BaseViewHolder<T> onCreateBaseViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        //重点在这里 还可能要处理一些网络请求的问题
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
        return super.getItemViewType(position);
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


    public void setHeader(View header) {
        hasHeader = true;
        headerView = header;
        mViewCount++;
    }

    public void setCell(ViewGroup cell) {
        cellView = cell;
        mViewCount++;
    }

    public void setFooter(View footer) {
        hasFooter = true;
        footerView = footer;
        mViewCount++;
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

}
