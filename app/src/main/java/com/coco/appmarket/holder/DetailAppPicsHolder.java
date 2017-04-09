package com.coco.appmarket.holder;

import java.util.ArrayList;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.utils.HttpHelper;
import com.coco.appmarket.utils.UIUtils;

/**
 * 详情页-应用图片
 */
public class DetailAppPicsHolder extends BaseHolder<AppInfo> {

	private ImageView[] mImageViews;

	@Override
	public View InitView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.layout_detail_picinfo, null);

		mImageViews = new ImageView[5];
		mImageViews[0] = (ImageView) view.findViewById(R.id.iv_pic1);
		mImageViews[1] = (ImageView) view.findViewById(R.id.iv_pic2);
		mImageViews[2] = (ImageView) view.findViewById(R.id.iv_pic3);
		mImageViews[3] = (ImageView) view.findViewById(R.id.iv_pic4);
		mImageViews[4] = (ImageView) view.findViewById(R.id.iv_pic5);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		if (data != null) {
			ArrayList<String> list = data.screen;
			for (int i = 0; i < 5; i++) {
				if (i < list.size()) {
					mImageViews[i].setVisibility(View.VISIBLE);
					Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + list.get(i)).into(mImageViews[i]);
				} else {
					mImageViews[i].setVisibility(View.GONE);
				}
			}
		}
	}

}
