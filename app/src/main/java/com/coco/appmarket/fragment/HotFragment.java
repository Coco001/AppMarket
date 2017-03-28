package com.coco.appmarket.fragment;

import android.view.View;

import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.utils.UIUtils;

/**
 * 排行
 */
public class HotFragment extends BaseFragment {

	@Override
	public View onCreateSuccessView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.fragment_hot, null);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		return null;
	}
}
