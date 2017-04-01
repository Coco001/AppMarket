package com.coco.appmarket.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * RecyclerView的baseadapter的封装
 */

public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> items = new ArrayList<>();
    private Map<Integer, Integer> layoutMap = new HashMap<>();

    public RecyclerViewBaseAdapter(Context context) {//使用Map存放viewType和layoutId的对应关系
        this(context, null);
    }

    public RecyclerViewBaseAdapter(Context context, int[] layoutId) {//使用Map存放viewType和layoutId的对应关系
        mContext = context;
        mInflater = LayoutInflater.from(context);
        for (int i = 0; i < layoutId.length; i++) {
            layoutMap.put(i, layoutId[i]);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(mInflater.inflate(getLayoutId(viewType), parent, false));
    }

    private int getLayoutId(int type) {
        return layoutMap.get(type);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(mContext, holder, items.get(position), bindLayout(items.get(position), position), position);
    }

    //绑定数据源
    protected abstract void bindData(Context context, RecyclerViewHolder holder, T item, int layoutId, int position);

    //获取每个item的viewType
    protected abstract int bindLayout(T item, int position);

    @Override
    public int getItemViewType(int position) {
        int layoutId = bindLayout(items.get(position), position);
        return getViewType(layoutId);
    }

    private int getViewType(int position) {
        Iterator<Map.Entry<Integer, Integer>> iterator = layoutMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Integer key = (Integer) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (val == position) {
                return key;
            }
        }
        return 0;
    }

    public Context getContext() {
        return mContext;
    }

    public void setData(List<T> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
