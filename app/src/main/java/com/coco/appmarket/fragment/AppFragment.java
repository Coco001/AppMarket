package com.coco.appmarket.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseApplication;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.base.MyBaseAdapter;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.holder.AppHolder;
import com.coco.appmarket.protocol.AppProtocol;
import java.util.ArrayList;

/**
 * 应用
 */
public class AppFragment extends BaseFragment {
	private ArrayList<AppInfo> mData;
	private Context mContext;
	@Override
	public View onCreateSuccessView() {
		mContext = BaseApplication.getContext();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_app, null);

		ListView listView = (ListView) view.findViewById(R.id.app_listview);
		AppAdapter adapter = new AppAdapter(mData);
		listView.setAdapter(adapter);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		AppProtocol protocol = new AppProtocol();
		mData = protocol.getData(0);
		return check(mData);
	}

	class AppAdapter extends MyBaseAdapter<AppInfo> {
		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		protected ArrayList<AppInfo> onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			ArrayList<AppInfo> moreData = protocol.getData(getListSize());
			return moreData;
		}

		@Override
		protected BaseHolder getHolder(int position) {
			return new AppHolder();
		}

	}

}
