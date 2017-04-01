package com.coco.appmarket.base;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coco.appmarket.R;
import com.coco.appmarket.utils.UIUtils;

/**
 * 加载更多的holder
 */

public class MoreHolder extends BaseHolder<Integer> {
    public static final int TYPE_MORE = 0;   //可以加载更多
    public static final int TYPE_NONE = 1;   //不可以加载更多
    public static final int TYPE_ERROR = 2;  //加载更多失败
    private TextView mLoadError;
    private LinearLayout mLoadMore;

    public MoreHolder(boolean hasMore) {
        setData(hasMore ? TYPE_MORE : TYPE_NONE);
    }

    @Override
    public View InitView() {
        View view = UIUtils.inflate(R.layout.layout_loading_more);
        mLoadMore = (LinearLayout) view.findViewById(R.id.ll_loading_more);
        mLoadError = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data) {
            case TYPE_MORE:
                mLoadMore.setVisibility(View.VISIBLE);
                mLoadError.setVisibility(View.GONE);
                break;
            case TYPE_NONE:
                mLoadMore.setVisibility(View.GONE);
                mLoadError.setVisibility(View.GONE);
                break;
            case TYPE_ERROR:
                mLoadMore.setVisibility(View.GONE);
                mLoadError.setVisibility(View.VISIBLE);
                break;
        }
    }
}
