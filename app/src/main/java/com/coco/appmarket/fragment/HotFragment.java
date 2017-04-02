package com.coco.appmarket.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.protocol.HotProtocol;
import com.coco.appmarket.utils.UIUtils;
import com.coco.appmarket.utils.flow.DrawableUtils;
import com.coco.appmarket.utils.flow.FlowLayout;
import java.util.ArrayList;
import java.util.Random;

/**
 * 排行
 */
public class HotFragment extends BaseFragment {

	private ArrayList<String> mData;

	@Override
	public View onCreateSuccessView() {
		int padding = UIUtils.dip2px(15);
		ScrollView view = new ScrollView(UIUtils.getContext());
		view.setPadding(padding, padding, padding, padding);
		//ScrollView view = (ScrollView) View.inflate(UIUtils.getContext(), R.layout.fragment_hot, null);
		FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
		//FlowLayout flowLayout = (FlowLayout) View.inflate(UIUtils.getContext(), R.layout.fragment_hot, null);
		//FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.hot_flow);
		for (int i = 0; i < mData.size(); i++) {
			final String keyWord = mData.get(i);
			TextView textView = new TextView(UIUtils.getContext());
			textView.setText(keyWord);
			textView.setTextColor(Color.WHITE);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			textView.setPadding(padding, padding, padding, padding);//设置内边界
			textView.setGravity(Gravity.CENTER);
			Random random = new Random();
			int r = 30 + random.nextInt(200);
			int g = 30 + random.nextInt(222);
			int b = 30 + random.nextInt(200);
			int color = 0xffcecece;//按下后偏白的背景
			Drawable drawable = DrawableUtils.getStateListDrawable(Color.rgb(r, g, b), color, UIUtils.dip2px(8));
			textView.setBackground(drawable);
			flowLayout.addView(textView);
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), keyWord, Toast.LENGTH_SHORT).show();
				}
			});
		}

		view.addView(flowLayout);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		HotProtocol protocol = new HotProtocol();
		mData = protocol.getData(0);
		return check(mData);
	}
}
