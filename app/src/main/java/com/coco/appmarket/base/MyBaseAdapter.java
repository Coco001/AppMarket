package com.coco.appmarket.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.coco.appmarket.utils.ThreadManager;
import com.coco.appmarket.utils.UIUtils;
import java.util.ArrayList;

/**
 * BaseAdapter的封装
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter{

    private static final int LOAD_MORE_DISABLE = 0; // 不提供加载更多的功能
    private static final int LOAD_MORE_ENABLE = 1;  // 提供加载更多的功能
    private boolean isLoadMore = false;
    private ArrayList<T> data;

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return LOAD_MORE_ENABLE;
        } else {
            return getInnerType(position);
        }
    }

    //普通的布局类型有可能返回多种类型，子类重写该方法，可以返回更多普通布局类型
    private int getInnerType(int position) {
        return LOAD_MORE_DISABLE;
    }

    public int getListSize() {//获取集合的大小
        return data.size();
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            holder = getItemViewType(position) == LOAD_MORE_ENABLE ? new MoreHolder(hasMore()) : getHolder(position);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        //刷新显示
        if (getItemViewType(position) != LOAD_MORE_ENABLE) {
            holder.setData(getItem(position));
        } else {
            MoreHolder moreHolder = (MoreHolder) holder;
            if (moreHolder.getData() == MoreHolder.TYPE_MORE) {
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    //加载更多数据
    public void loadMore(final MoreHolder holder) {
        if (!isLoadMore) {
            isLoadMore = true;
            ThreadManager.getThreadPool().execute(new Runnable() {

                @Override
                public void run() {
                    // 获取更多数据
                    final ArrayList<T> moreList = onLoadMore();
                    UIUtils.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (moreList == null) {// 如果没有拿到数据,说明加载失败
                                holder.setData(MoreHolder.TYPE_ERROR);
                            } else {
                                // 每页返回20条数据,如果发现获取数据小于20条,说明已经没有更多数据了
                                if (moreList.size() < 20) {
                                    holder.setData(MoreHolder.TYPE_NONE);
                                } else {
                                    holder.setData(MoreHolder.TYPE_MORE);
                                }

                                // 将下一页的数据追加到当前集合当中
                                data.addAll(moreList);
                                // 刷新当前listview
                                notifyDataSetChanged();
                            }
                            isLoadMore = false;
                        }
                    });
                }
            });
        }
    }

    protected abstract ArrayList<T> onLoadMore();

    public  boolean hasMore(){
        return true;
    }

    protected abstract BaseHolder getHolder(int position);
}
