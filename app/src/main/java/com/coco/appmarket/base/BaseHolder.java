package com.coco.appmarket.base;

import android.view.View;

/**
 * ViewHolder的封装
 */

public abstract class BaseHolder<T> {
    private View mRootView;//item的根布局
    private T data;

    public BaseHolder() {
        mRootView = InitView();
        mRootView.setTag(this);//打一个标签
    }

    public abstract View InitView();//初始化布局并加载组件

    public abstract void refreshView(T data);//刷新显示

    //设置item的数据
    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    //获取item数据
    public T getData() {
        return data;
    }

    public View getRootView() {
        return mRootView;
    }
}
