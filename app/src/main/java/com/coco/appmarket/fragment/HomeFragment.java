package com.coco.appmarket.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coco.appmarket.R;
import com.coco.appmarket.base.BaseApplication;
import com.coco.appmarket.base.BaseFragment;
import com.coco.appmarket.base.LoadingPage;
import com.coco.appmarket.base.BaseHolder;
import com.coco.appmarket.base.MyBaseAdapter;
import com.coco.appmarket.bean.AppInfo;
import com.coco.appmarket.holder.HomeHolder;
import com.coco.appmarket.protocol.HomeProtocol;
import com.coco.appmarket.utils.UIUtils;
import java.util.ArrayList;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

	private ArrayList<AppInfo> mData;
	private Context mContext;

	@Override
	public View onCreateSuccessView() {
		mContext = BaseApplication.getContext();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_home, null);

		ListView listView = (ListView) view.findViewById(R.id.home_listview);
		HomeAdapter adapter = new HomeAdapter(mData);
		listView.setAdapter(adapter);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		HomeProtocol protocol = new HomeProtocol();
		mData = protocol.getData(0);

		return check(mData);
	}

	class HomeAdapter extends MyBaseAdapter<AppInfo> {
		public HomeAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		protected ArrayList<AppInfo> onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			ArrayList<AppInfo> moreData = protocol.getData(getListSize());
			return moreData;
		}

		@Override
		protected BaseHolder getHolder(int position) {
			return new HomeHolder();
		}

	}

}
