package com.coco.appmarket.holder;

import android.view.View;
import android.widget.TextView;

import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.bean.CategoryInfo;
import com.coco.appmarket.utils.UIUtils;

/**
 * 分类页标题栏holder
 */
public class TitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tvTitle;

	@Override
	public View InitView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.list_item_title, null);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView(CategoryInfo data) {
		tvTitle.setText(data.title);
	}

}
