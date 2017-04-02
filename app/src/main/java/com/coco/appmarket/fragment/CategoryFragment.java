package com.coco.appmarket.fragment;

import android.view.View;

import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.base.MyBaseAdapter;
import com.coco.appmarket.base.MyListView;
import com.coco.appmarket.bean.CategoryInfo;
import com.coco.appmarket.holder.CategoryHolder;
import com.coco.appmarket.holder.TitleHolder;
import com.coco.appmarket.protocol.CategoryProtocol;
import com.coco.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 分类
 */
public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new CateAdapter(mData));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class CateAdapter extends MyBaseAdapter<CategoryInfo> {
        public CateAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public int getViewTypeCount() {//在原有的基础上增加一种标题类型
            return super.getViewTypeCount() + 1;
        }

        @Override
        public int getInnerType(int position) {
            CategoryInfo info = mData.get(position);
            if (info.isTitle) {//返回标题类型
                return super.getInnerType(position) + 2;
            } else {//返回普通类型
                return super.getInnerType(position);
            }
        }

        @Override
        protected BaseHolder getHolder(int position) {
            CategoryInfo info = mData.get(position);
            if (info.isTitle) {//返回标题类型的holder
                return new TitleHolder();
            }
            //返回普通类型的holder
            return new CategoryHolder();

        }

        @Override
        protected ArrayList<CategoryInfo> onLoadMore() {
            return null;
        }
    }

}
