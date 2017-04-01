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
import com.coco.appmarket.bean.SubjectInfo;
import com.coco.appmarket.holder.SubHolder;
import com.coco.appmarket.protocol.SubProtocol;
import java.util.ArrayList;

/**
 * 专题
 */
public class SubjectFragment extends BaseFragment {
	private ArrayList<SubjectInfo> mData;
	private Context mContext;
	@Override
	public View onCreateSuccessView() {
		mContext = BaseApplication.getContext();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_subject, null);

		ListView listView = (ListView) view.findViewById(R.id.sub_listview);
		SubAdapter adapter = new SubAdapter(mData);
		listView.setAdapter(adapter);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		SubProtocol protocol = new SubProtocol();
		mData = protocol.getData(0);

		return check(mData);
	}

	class SubAdapter extends MyBaseAdapter<SubjectInfo> {

		public SubAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		protected ArrayList<SubjectInfo> onLoadMore() {
			SubProtocol protocol = new SubProtocol();
			ArrayList<SubjectInfo> moreData = protocol.getData(getListSize());
			return moreData;
		}

		@Override
		protected BaseHolder getHolder(int position) {
			return new SubHolder();
		}

	}

}
